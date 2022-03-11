package bluepixel;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public final class Steganographer {
    private Steganographer() {
    }

    public static String extract(BufferedImage image) {
        StringBuilder message = new StringBuilder();
        int symbol = 0;
        int counter = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);
                symbol = (symbol << 1) | (pixel & 1);
                if (++counter == Character.SIZE) {
                    message.append((char)  symbol);
                    symbol = counter = 0;
                }
            }
        }
        return message.toString();
    }

    public static BufferedImage insert(BufferedImage image, String string) {
        BufferedImage codedImage = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_ARGB);
        int imageSize = image.getWidth() * image.getHeight();
        if (imageSize < string.length()) {
            string = string.substring(0, imageSize);
        }
        int counter = 15;
        int symbolPos = 0;
        int symbol = string.charAt(0);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);
                pixel = (pixel >>> 1) << 1;
                int bit = (symbol & (1 << counter)) >>> counter;
                pixel = pixel | bit;
                codedImage.setRGB(j, i, pixel);
                if (counter-- == 0) {
                    counter = 15;
                    symbolPos++;
                    if (symbolPos < string.length()) {
                        symbol = string.charAt(symbolPos);
                    } else {
                        symbol = 0;
                    }
                }
            }
        }
        return codedImage;
    }
}
