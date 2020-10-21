import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author gongc
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        HelloClassLoader classLoader = new HelloClassLoader();
        try {
            Class<?> hello = classLoader.findClass("Hello");
            Object objHello = hello.newInstance();
            Method method = hello.getMethod("hello");
            method.invoke(objHello);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getClassByte("D:/code/Hello.xlass");
        return defineClass(name,bytes,0,bytes.length);
    }

    private byte[] getClassByte(String path){
        File file = new File(path);
        byte[] tempBytes = new byte[(int) file.length()];
        FileInputStream in = null;
        try {
           in =  new FileInputStream(file);
           in.read(tempBytes);

            for (int i = 0; i < tempBytes.length; i++) {
                tempBytes[i] = (byte) (255 - tempBytes[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tempBytes;
    }
}
