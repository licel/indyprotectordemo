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

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Protects bytecode with invokedynamic
 * 
 * @author Mikhail Dudarev (dudarev@licelus.com)
 */
public class ClassProtector extends ClassVisitor implements Opcodes {    

    String className = null;
    BootstrapMethodGenerator bsmg;    
    
    public ClassProtector(ClassVisitor cv) {
        super(ASM5, cv);
    }
    
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = "L"+name+";";
        bsmg = new BootstrapMethodGenerator(className);   
        super.visit(version, access, name, signature, superName, interfaces);
    }    
    
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        // skip bootstrap method
        if((name+desc).equals(BootstrapMethodGenerator.BSM_NAME+BootstrapMethodGenerator.BSM_SIG)){
            return mv;
        }
        return new MethodIndyProtector(mv, bsmg.getBootsrapMethodHandle());
    }
    
    @Override
    public void visitEnd() {
        bsmg.insertMethod(this);
        cv.visitEnd();
    }    
    
}
