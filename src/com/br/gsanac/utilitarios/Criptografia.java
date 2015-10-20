package com.br.gsanac.utilitarios;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jonathan Marcos
 */
public class Criptografia {

    /**
     * @author Jonathan Marcos
     * @date 08/09/2014
     * @param num
     *            Numero a ser rotacionado
     * @param cnt
     *            Potencia de 10
     * @return Representação base64 do seu SHA-1
     */
	/*
	 * Realiza a rotação bitwise 
	 * de um número 32-bits para a esquerda
	 */
    private static int rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    /**
     * @author Jonathan Marcos
     * @date 08/09/2014
     * @param str
     *            String a ser convertida
     * @return Representação base64 do seu SHA-1
     */
    /*
     * Recebe a string e retorna 
     * a representação base64 do seu SHA-1
     */
    public static String encode(String str) {

        /*
         *  Converte a string em uma sequencia 
         *  de 16 blocos de palavras, guardadas num vetor.
         *  Adiciona os bits e o tamanho, conforme 
         *  descrito no SHA1 padrão
         */

        byte[] x = str.getBytes();
        int[] blks = new int[(((x.length + 8) >> 6) + 1) * 16];
        int i;

        for (i = 0; i < x.length; i++) {

            blks[i >> 2] |= x[i] << (24 - (i % 4) * 8);

        }

        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);

        blks[blks.length - 1] = x.length * 8;

        // Calcula o hash 160 bit SHA1 de sequencia de blocos

        int[] w = new int[80];

        int a = 1732584193;

        int b = -271733879;

        int c = -1732584194;

        int d = 271733878;

        int e = -1009589776;

        for (i = 0; i < blks.length; i += 16) {

            int olda = a;

            int oldb = b;

            int oldc = c;

            int oldd = d;

            int olde = e;

            for (int j = 0; j < 80; j++) {

                w[j] = (j < 16) ? blks[i + j] : (rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1));

                int t = rol(a, 5)
                        + e
                        + w[j]
                        + ((j < 20) ? 1518500249 + ((b & c) | ((~b) & d)) : (j < 40) ? 1859775393 + (b ^ c ^ d) : (j < 60) ? -1894007588
                                + ((b & c) | (b & d) | (c & d)) : -899497514 + (b ^ c ^ d));

                e = d;

                d = c;

                c = rol(b, 30);

                b = a;

                a = t;

            }

            a = a + olda;

            b = b + oldb;

            c = c + oldc;

            d = d + oldd;

            e = e + olde;

        }

        // Converte 160 bit has para base64

        int[] words = {
                a,
                b,
                c,
                d,
                e,
                0
        };

        byte[] base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes();

        byte[] result = new byte[28];

        for (i = 0; i < 27; i++) {

            int start = i * 6;

            int word = start >> 5;

            int offset = start & 0x1f;

            if (offset <= 26) {

                result[i] = base64[(words[word] >> (26 - offset)) & 0x3F];

            } else if (offset == 28) {

                result[i] = base64[(((words[word] & 0x0F) << 2) |

                ((words[word + 1] >> 30) & 0x03)) & 0x3F];

            } else {

                result[i] = base64[(((words[word] & 0x03) << 4) |

                ((words[word + 1] >> 28) & 0x0F)) & 0x3F];

            }

        }

        result[27] = '=';

        return new String(result);
    }

    /**
     * Calcula o hash de um array de bytes usando o algoritmo SHA-1.
     * 
     * @author André Miranda
     * @date 19/05/2015
     * 
     * @param data Array de bytes para ser computado o seu hash
     * @return String do hash em hexadecimal
     * 
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
    public static String hash(byte[] data) throws NoSuchAlgorithmException, IOException {
    	FileInputStream fis = null;

    	try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(data);
			byte[] mdbytes = md.digest();

			// converte o array de bytes para hexadecimal
			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				hash.append(Integer.toHexString(0xFF & mdbytes[i]));
			}

			return hash.toString();
		} finally {
			try {
				if(fis != null) fis.close();
			} catch (Exception ignorar) {}
		}
    }
}