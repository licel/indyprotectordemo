/*
 * Copyright 2015 Licel Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javaone2015.con7442.indyprotector;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Protects input class file
 * 
 * @author Mikhail Dudarev (dudarev@licelus.com)
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        if(args.length!=2){
            System.out.println("Usage: java -jar IndyProtectorDemo-1.0.jar <input class> <output class>");
            System.exit(-1);
        }
        try (
                InputStream in = Files.newInputStream(Paths.get(args[0]));
                OutputStream out = Files.newOutputStream(Paths.get(args[1]));
            ) {
            ClassReader cr = new ClassReader(in);
            ClassWriter cw = new ClassWriter(0);
            ClassProtector cp = new ClassProtector(cw);
            cr.accept(cp, 0);
            out.write(cw.toByteArray());
        }
    }
}
