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

import java.security.SecureRandom;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Replaces invoke* instructions with invokedynamic
 * 
 * @author Mikhail Dudarev (dudarev@licelus.com)
 */
public class MethodIndyProtector extends MethodVisitor implements Opcodes {
    Handle bootstrapMethodHandle = null;
    SecureRandom rnd = new SecureRandom();
    
    public MethodIndyProtector(MethodVisitor mv,Handle bootstrapMethodHandle) {
        super(ASM5, mv);
        this.bootstrapMethodHandle = bootstrapMethodHandle;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        boolean isStatic = (opcode == Opcodes.INVOKESTATIC);
        String newSig = isStatic ? desc : desc.replace("(", "(Ljava/lang/Object;");
        Type origReturnType = Type.getReturnType(newSig);
        Type[] args = Type.getArgumentTypes(newSig);
        for (int i = 0; i < args.length; i++) {
            args[i] = genericType(args[i]);
        }
        newSig = Type.getMethodDescriptor(origReturnType, args);
        switch (opcode) {
            case INVOKESTATIC: // invokestatic opcode
            case INVOKEVIRTUAL: // invokevirtual opcode
            case INVOKEINTERFACE: // invokeinterface opcode
                mv.visitInvokeDynamicInsn(String.valueOf(rnd.nextInt()), newSig, bootstrapMethodHandle, opcode, owner.replaceAll("/", "."), name, desc);
                if (origReturnType.getSort() == Type.ARRAY) {
                    mv.visitTypeInsn(Opcodes.CHECKCAST, origReturnType.getInternalName());
                }
            break;
            default:
                mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    private Type genericType(Type type) {
        Type newType;
        switch (type.getSort()) {
            case Type.OBJECT:
                newType = Type.getType(Object.class);
                break;
            default:
                newType = type;
                break;
        }
        return newType;
    }
}
