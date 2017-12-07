package com.utils.serialize;

import java.io.*;

/**
 * <p>
 * Object 序列化和反序列化
 * </p>
 *
 * @author wangliang
 * @since 2017/12/7
 */
public class SerializeUtil {

    public static Object read(String path) throws IOException, ClassNotFoundException {
        InputStream fis = null;
        ObjectInputStream ois = null;
        Object o = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            o = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return o;
    }

    public static void write(Object object, String path) throws IOException {
        OutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

}
