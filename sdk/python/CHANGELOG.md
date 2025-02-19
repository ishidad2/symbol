# Changelog
All notable changes to this project will be documented in this file.

The changelog format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## next

## [3.0.11] - 27-Jul-2023

### Added
 - lookup_transaction_name for generating friendly transaction name from transaction type and version
 - (Symbol-only) special handling for encrypted messages created by Symbol wallets

### Fixed
 - (NEM-only) rename TransactionType enum value MULTISIG_TRANSACTION to MULTISIG
 - (Symbol-only) error that occurs when setting MosaicFlags in the Mosaic definition transaction

## [3.0.7] - 27-Apr-2023

### Changed
 - Network
   - to_datetime and from_datetime promoted from NetworkTimestamp to Network
   - NetworkTimestamp moved into Network
   - Add epoch_time to network description
   - Adjust testnet epoch time to match sai network
 - MerkleHashBuilder.py renamed to Merkle.py
 - Facade
   - Can be created around Network instance or name
 - Sign / verify canonical signature checks
 - TransactionFactory supports auto sorting transaction properties
 - Bip32.random for generating random mnemonic
 - Support `repr` in ByteArray derived types
 - ripemd fallback when it is not available in hashlib

### Added
 - is_valid_address_string to Network for checking validity of an unparsed address
 - MessageEncoder for encrypting and decrypting messages
   - AesCbcCipher (NEM only) and AesGcmCipher implementations
   - SharedKey256 BaseArray derived type
 - Facade
   - SharedKey type
   - bip32_path function for returning BIP32 compatible path
   - (Symbol-only) cosign_transaction for cosigning Symbol transactions
 - Symbol
   - Functions for verifying Merkle proofs and Merkle patricia proofs
   - Utility function metadata_update_value for simplifying update of metadata values
 - Proper handling of catbuffer computed fields/properties
 - (NEM-only) Automatic population for fields levy_size and message_envelope_size

### Fixed
 - (NEM-only) Add NonVerifiableMultisigTransactionV1 model required for signing 'multisig_transaction_v1'

## [3.0.3] - 14-Mar-2022

### Changed
 - cosmetic changes in generated code

### Fixed
 - testnet nemesis seed hash
 - properly handle last element in variable arrays (use `is_last_element_padded` in printers)

## [3.0.2] - 20-Jan-2022

### Changed
 - added support for generated nem-transactions, make sure to read [Breaking changes](BREAKING-CHANGES.md)

## [3.0.1] - 10-Jan-2022

### Changed
 - added support for output generated by new lark-based parser, use new features minimizing amount of hacks in the process

## [3.0.0] - unreleased

### Changed
 - breaking changes, details inside [Breaking changes](BREAKING-CHANGES.md)

## [2.0.0] - 30-Aug-2021

### Changed
 - rename nis1 => nem
 - rename sym => symbol

## [1.0.3] - 25-Jul-2021

### Added
 - add find_by_address to AccountDescriptorRepository

### Fixed
 - add mapping for voting key dto

## 1.0.0 - 22-Apr-2021

### Added
 - initial code release

[3.0.11]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv3.0.7...sdk%2Fpython%2Fv3.0.11
[3.0.7]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv3.0.3...sdk%2Fpython%2Fv3.0.7
[3.0.3]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv3.0.2...sdk%2Fpython%2Fv3.0.3
[3.0.2]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv3.0.1...sdk%2Fpython%2Fv3.0.2
[3.0.1]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv3.0.0...sdk%2Fpython%2Fv3.0.1
[3.0.0]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv2.0.0...sdk%2Fpython%2Fv3.0.0
[2.0.0]: https://github.com/symbol/symbol/compare/sdk%2Fpython%2Fv1.0.3...sdk%2Fpython%2Fv2.0.0
[1.0.3]: https://github.com/symbol/symbol/releases/tag/sdk%2Fpython%2Fv1.0.3
