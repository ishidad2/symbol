pipeline {
	parameters {
		gitParameter branchFilter: 'origin/(.*)', defaultValue: 'dev', name: 'MANUAL_GIT_BRANCH', type: 'PT_BRANCH'
		choice name: 'ARCHITECTURE', choices: ['amd64', 'arm64'], description: 'Computer architecture'
		booleanParam name: 'SHOULD_PUBLISH_JOB_STATUS', description: 'true to publish job status', defaultValue: true
	}

	agent {
		label "${helper.resolveAgentName('ubuntu', "${ARCHITECTURE}", 'small')}"
	}

	options {
		ansiColor('css')
		timestamps()
	}

	triggers {
		// saturday and sunday of the week
		cron('H H * * 6,7')
	}

	stages {
		stage('override architecture') {
			when {
				triggeredBy 'TimerTrigger'
			}
			steps {
				script {
					// even days are amd64, odd days are arm64
					ARCHITECTURE = helper.determineArchitecture()
				}
			}
		}
		stage('print env') {
			steps {
				echo """
							env.GIT_BRANCH: ${env.GIT_BRANCH}
						 MANUAL_GIT_BRANCH: ${MANUAL_GIT_BRANCH}
							  ARCHITECTURE: ${ARCHITECTURE}
				"""
			}
		}

		stage('build ci images') {
			parallel {
				stage('cpp') {
					steps {
						script {
							dispatchBuildCiImageJob('cpp')
						}
					}
				}
				stage('java') {
					steps {
						script {
							dispatchBuildCiImageJob('java')
						}
					}
				}
				stage('javascript') {
					steps {
						script {
							dispatchBuildCiImageJob('javascript')
						}
					}
				}
				stage('linter') {
					steps {
						script {
							dispatchBuildCiImageJob('linter')
						}
					}
				}
				stage('postgres') {
					steps {
						script {
							dispatchBuildCiImageJob('postgres')
						}
					}
				}

				stage('python') {
					steps {
						script {
							dispatchBuildCiImageJob('python')
						}
					}
				}
				stage('python - ubuntu:20.04') {
					steps {
						script {
							dispatchBuildCiImageJob('python', 'ubuntu:20.04')
						}
					}
				}
				stage('python - ubuntu:23.04') {
					steps {
						script {
							dispatchBuildCiImageJob('python', 'ubuntu:23.04')
						}
					}
				}
			}
		}
	}
	post {
		success {
			script {
				if (env.SHOULD_PUBLISH_JOB_STATUS?.toBoolean()) {
					helper.sendDiscordNotification(
						':confetti_ball: CI Image All Job Successfully completed',
						'Not much to see here, all is good',
						env.BUILD_URL,
						currentBuild.currentResult
					)
				}
			}
		}
		unsuccessful {
			script {
				if (env.SHOULD_PUBLISH_JOB_STATUS?.toBoolean()) {
					helper.sendDiscordNotification(
						":worried: CI Image All Job Failed for ${currentBuild.fullDisplayName}",
						"At least one job failed for Build#${env.BUILD_NUMBER} which has a result of ${currentBuild.currentResult}.",
						env.BUILD_URL,
						currentBuild.currentResult
					)
				}
			}
		}
	}
}

void dispatchBuildCiImageJob(String ciImage, String baseImage = 'ubuntu:22.04') {
	build job: 'build-ci-image', parameters: [
		string(name: 'CI_IMAGE', value: "${ciImage}"),
		string(name: 'MANUAL_GIT_BRANCH', value: "${params.MANUAL_GIT_BRANCH}"),
		string(name: 'ARCHITECTURE', value: "${params.ARCHITECTURE}"),
		string(name: 'BASE_IMAGE', value: "${baseImage}"),
		booleanParam(
			name: 'SHOULD_PUBLISH_FAIL_JOB_STATUS',
			value: "${!env.SHOULD_PUBLISH_JOB_STATUS || env.SHOULD_PUBLISH_JOB_STATUS.toBoolean()}"
		)
	]
}
