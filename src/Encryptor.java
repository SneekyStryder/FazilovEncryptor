import java.util.ArrayList;

public class Encryptor {
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c) {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock() {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str) {
        String[] dump = new String[str.length()];
        dump = str.split("");
        ArrayList<String> characters = new ArrayList<>();
        for (int i = 0; i < dump.length; i++) {
            characters.add(dump[i]);
        }
        if (characters.size() < numRows * numCols) {
            while (characters.size() != numRows * numCols) {
                characters.add("A");
            }
        }

        String[][] list = new String[numRows][numCols];
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                list[i][j] = characters.get(0);
                characters.remove(0);
            }
        }

        letterBlock = list;
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock() {
        String encrypted = "";
         for (int i = 0; i < letterBlock[0].length; i++) {
             for (int j = 0; j < letterBlock.length; j++) {
                 encrypted += letterBlock[j][i];
             }
         }
         return encrypted;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        String encryptedMessage = "";
        int size = numCols * numRows;
        while (message.length() > 0) {
            if (size > message.length()) {
                size = message.length();
            }
            fillBlock(message);
            encryptedMessage += encryptBlock();
            message = message.substring((size));
        }
        return encryptedMessage;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage) {
        String decryptedMessage = "";
        int size = numCols * numRows;
        while (encryptedMessage.length() > 0) {
            String chunkToDecrypt = encryptedMessage.substring(0, size);
            decryptedMessage += decryptBlock(chunkToDecrypt);
            encryptedMessage = encryptedMessage.substring((size));
        }
        String lastChar = decryptedMessage.substring((decryptedMessage.length() - 1));
        while (lastChar.equals("A")) {
            decryptedMessage = decryptedMessage.substring(0, decryptedMessage.length() - 1);
            lastChar = decryptedMessage.substring(decryptedMessage.length() - 1);
        }

        return decryptedMessage;
    }

    private String decryptBlock(String blockStr) {
        String[][] temp2D = new String[numRows][numCols];
        int pos = 0;
        for (int col = 0; col < temp2D[0].length; col++) {
            for (int row = 0; row < temp2D.length; row++) {
                temp2D[row][col] = blockStr.substring(pos, pos + 1);
                pos++;
            }
        }

        String decryptedBlock = "";
        for (int row = 0; row < temp2D.length; row++) {
            for (int col = 0; col < temp2D[0].length; col++) {
                String element = temp2D[row][col];
                decryptedBlock += element;
            }
        }
        return decryptedBlock;
    }
}
