package model.filters.masks.impl;

import model.filters.masks.Mask;

import java.awt.*;

/**
 * @author Esteban I. Invernizzi
 * @created 08/04/2010
 */
public class NormalizedMask implements Mask {

    // TODO Solucionar la repeticion de codigo de ClampingMask

    private double[][] coefs;
    private int width, height;
    int imgpixels[], newimgpixels[];

    public static NormalizedMask create(double[][] coefs) throws InvalidMaskException {

        testCreate(coefs);
        NormalizedMask result = new NormalizedMask();
        result.coefs = coefs;
        return result;
    }

    public Image apply(Image image) {
        NormalizedMaskConvolver convolver = new NormalizedMaskConvolver();
        return convolver.filter(image);
    }

    private static void testCreate(double[][] coefs) throws InvalidMaskException {
        int width = coefs.length;
        if ((width < 3) || (width % 2 == 0))
            throw new InvalidMaskException();
        int height = coefs[0].length;
        if ((height < 3) || (height % 2 == 0))
            throw new InvalidMaskException();
        for(int i = 0; i < coefs.length; i++)
            if (coefs[i].length != height)
                throw new InvalidMaskException();
    }

    private NormalizedMask() {}

    private class NormalizedMaskConvolver extends Convolver {

        @Override
        public void convolve() {

            for(int y=1; y<height-1; y++) {
                for(int x=1; x<width-1; x++) {
                    int newR = 0;
                    int newG = 0;
                    int newB = 0;

                    for(int k=-1; k<=1; k++) {
                        for(int j=-1; j<=1; j++){
                            int rgb = imgpixels[ (y+k)*width + (x+j) ];
                            int r = (rgb >> 16) & 0xff;
                            int g = (rgb >> 8) & 0xff;
                            int b = rgb & 0xff;

                            newR += r * coefs[j+1][k+1];
                            newG += g * coefs[j+1][k+1];
                            newB += b * coefs[j+1][k+1];
                        }
                    }
                    newimgpixels[y*width+x] = (0xff000000 |
                    clamp(newR) << 16 | clamp(newG) << 8 | clamp(newB));
                }
            }
        }

        private int clamp(int c) {
            // TODO Implementar una normalizacion distinta
            return ( c > 255 ? 255 : (c < 0 ? 0 : c));
        }
    }

}