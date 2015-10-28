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
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Bytecode generator for the bootstrap method
 *
 * @author Mikhail Dudarev (dudarev@licelus.com)
 */
public class BootstrapMethodGenerator implements Opcodes {

    public static final String BSM_NAME = "bootstrap$0";
    public static final String BSM_SIG = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;";
    private Handle bootsrapMethodHandle = null;
    private String targetClassName = null;

    public BootstrapMethodGenerator(String targetClassName) {
        this.targetClassName = targetClassName;
        bootsrapMethodHandle = new Handle(Opcodes.H_INVOKESTATIC, targetClassName, BSM_NAME, BSM_SIG);
    }

    /**
     * Generate bootstrap method
     */
    public void insertMethod(ClassVisitor target) {
        MethodVisitor mv = target.visitMethod(ACC_PRIVATE + ACC_STATIC, BSM_NAME, BSM_SIG, null, null);
        mv.visitCode();
        Label l0 = new Label();
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, 7);
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 4);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
        mv.visitVarInsn(ASTORE, 8);
        mv.visitLdcInsn(Type.getType(targetClassName));
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;", false);
        mv.visitVarInsn(ASTORE, 9);
        mv.visitVarInsn(ALOAD, 6);
        mv.visitVarInsn(ALOAD, 9);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/invoke/MethodType", "fromMethodDescriptorString", "(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;", false);
        mv.visitVarInsn(ASTORE, 10);
        mv.visitVarInsn(ILOAD, 3);
        Label l3 = new Label();
        Label l4 = new Label();
        Label l5 = new Label();
        mv.visitTableSwitchInsn(182, 185, l4, new Label[]{l3, l4, l5, l3});
        mv.visitLabel(l5);
        mv.visitFrame(Opcodes.F_FULL, 11, new Object[]{"java/lang/invoke/MethodHandles$Lookup", "java/lang/String", "java/lang/invoke/MethodType", Opcodes.INTEGER, "java/lang/String", "java/lang/String", "java/lang/String", "java/lang/invoke/MethodHandle", "java/lang/Class", "java/lang/ClassLoader", "java/lang/invoke/MethodType"}, 0, new Object[]{});
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 8);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitVarInsn(ALOAD, 10);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/invoke/MethodHandles$Lookup", "findStatic", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;", false);
        mv.visitVarInsn(ASTORE, 7);
        Label l6 = new Label();
        mv.visitJumpInsn(GOTO, l6);
        mv.visitLabel(l3);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 8);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitVarInsn(ALOAD, 10);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/invoke/MethodHandles$Lookup", "findVirtual", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;", false);
        mv.visitVarInsn(ASTORE, 7);
        mv.visitJumpInsn(GOTO, l6);
        mv.visitLabel(l4);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitTypeInsn(NEW, "java/lang/BootstrapMethodError");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/BootstrapMethodError", "<init>", "()V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l6);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(ALOAD, 7);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/invoke/MethodHandle", "asType", "(Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;", false);
        mv.visitVarInsn(ASTORE, 7);
        mv.visitLabel(l1);
        Label l7 = new Label();
        mv.visitJumpInsn(GOTO, l7);
        mv.visitLabel(l2);
        mv.visitFrame(Opcodes.F_FULL, 8, new Object[]{"java/lang/invoke/MethodHandles$Lookup", "java/lang/String", "java/lang/invoke/MethodType", Opcodes.INTEGER, "java/lang/String", "java/lang/String", "java/lang/String", "java/lang/invoke/MethodHandle"}, 1, new Object[]{"java/lang/Exception"});
        mv.visitVarInsn(ASTORE, 8);
        mv.visitTypeInsn(NEW, "java/lang/BootstrapMethodError");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/BootstrapMethodError", "<init>", "()V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l7);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitTypeInsn(NEW, "java/lang/invoke/ConstantCallSite");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, 7);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/invoke/ConstantCallSite", "<init>", "(Ljava/lang/invoke/MethodHandle;)V", false);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(4, 11);
        mv.visitEnd();

    }

    public Handle getBootsrapMethodHandle() {
        return bootsrapMethodHandle;
    }
}
