package uk.gov.hmcts.reform.digital.signatures;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.security.KeyPair;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DigitalSignatureTest {

    private byte[] unSignedZipFile;

    @Before
    public void setUp() throws Exception {
        unSignedZipFile = Resources.toByteArray(Resources.getResource("1_24-06-2018-00-00-00.zip"));
    }

    @Test
    public void should_sign_and_verify_signature_and_return_true_when_zip_file_is_not_tampered() throws Exception {
        KeyPair keyPair = SignatureUtil.generateKeyPair();

        byte[] digitalSignature = SignatureUtil.signData(unSignedZipFile, keyPair.getPrivate());

        boolean verified = SignatureUtil.verifySignature(unSignedZipFile, keyPair.getPublic(), digitalSignature);

        assertTrue("Signature is verified", verified);
    }

    @Test
    public void should_sign_and_verify_signature_and_return_false_when_zip_file_is_tampered() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byteArrayOutputStream.write(unSignedZipFile, 0, unSignedZipFile.length);
        byteArrayOutputStream.write("spy".getBytes());

        byte[] modifiedZipBytes = byteArrayOutputStream.toByteArray();

        KeyPair keyPair = SignatureUtil.generateKeyPair();

        byte[] digitalSignature = SignatureUtil.signData(unSignedZipFile, keyPair.getPrivate());

        boolean verified = SignatureUtil.verifySignature(modifiedZipBytes, keyPair.getPublic(), digitalSignature);

        assertFalse("Signature is not verified as original bytes were modified", verified);
    }

    @Test
    public void should_sign_and_verify_signature_and_return_false_when_trying_to_sign_and_verify_with_different_keypairs() throws Exception {
        KeyPair keyPair1 = SignatureUtil.generateKeyPair();

        KeyPair keyPair2 = SignatureUtil.generateKeyPair();

        byte[] digitalSignature = SignatureUtil.signData(unSignedZipFile, keyPair1.getPrivate());

        boolean verified = SignatureUtil.verifySignature(unSignedZipFile, keyPair2.getPublic(), digitalSignature);

        assertFalse("Signature is not verified as keys were different", verified);
    }

}
