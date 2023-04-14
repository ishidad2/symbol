import webpack from 'webpack';
import path from 'path';
import URL from 'url';

const outputDirectory = path.resolve(path.dirname(URL.fileURLToPath(import.meta.url)), '_build');

const createBasicConfig = target => ({
	entry: './src/index.js',
	mode: process.env.NODE_ENV || 'development',
	target,

	output: {
		path: outputDirectory,
		filename: `bundle.${target}.js`
	},

	plugins: [
		new webpack.NormalModuleReplacementPlugin(
			/..\/..\/_build\/wasm\/node\/symbol_crypto_wasm/,
			`${outputDirectory}/wasm/${target}/symbol_crypto_wasm.js`
		)
	],

	experiments: {
		asyncWebAssembly: true
	}
});

const nodeConfig = createBasicConfig('node');
const webConfig = createBasicConfig('web');

// ESM module
webConfig.output.library = { type: 'module' };
webConfig.experiments.outputModule = true;

// add plugins and resolvers for setting up node to browser mappings
// not including WasmPackPlugin because we're building WASM as external step
webConfig.plugins = [
	new webpack.ProvidePlugin({
		process: 'process/browser',
		Buffer: ['buffer', 'Buffer']
	}),
	webConfig.plugins[0]
];
webConfig.resolve = {
	extensions: ['.js'],
	fallback: {
		crypto: 'crypto-browserify',
		stream: 'stream-browserify'
	}
};

export default [nodeConfig, webConfig];
