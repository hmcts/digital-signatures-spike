# digital-signatures-spike

This spike is done to test file non repuditiation whcih is implemented using digital signatures. A valid digital signature gives HMCTS Bulk Scan core processor reason to believe that the message was created by a known scanning supplier, that the supplier cannot deny having sent the message (non-repudiation), and that the message was not altered in transit (integrity).

### Generate a Public-Private Key Pair
The code to generate Public-Private Key Pair is identical to the one used in Asymmetric Cryptography example, please refer to – SignatureUtils.java

### Sign the message
Next we are signing zip file using private key generated in previous step. The original zip and the signature are separate files.

### Verify the Signature
The receiver has the original zip file and the digital signature and verifies the message using public key to verify that the message comes from the expected source with a pre-shared Public Key.
