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

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Template of the bootstrap method
 * 
 * @author Mikhail Dudarev (dudarev@licelus.com)
 */
public class BootstrapMethodTemplate {
    
    /**
     * Template of the bootstrap method
     * @param lookup
     * @param callerName
     * @param callerType
     * @param originalOpcode
     * @param originalClassName
     * @param originalMethodName
     * @param originalMethodSignature
     * @return
     */
    private static Object bootstrap$0(
            MethodHandles.Lookup lookup,
            String callerName,
            MethodType callerType,
            int originalOpcode,
            String originalClassName,
            String originalMethodName,
            String originalMethodSignature) {
        
        MethodHandle mh = null;
        try {
            // variables initialization
            Class clazz = Class.forName(originalClassName);
            ClassLoader currentClassLoader = BootstrapMethodTemplate.class.getClassLoader();
            MethodType originalMethodType = MethodType.fromMethodDescriptorString(originalMethodSignature, currentClassLoader);
            // lookup method handle
            switch (originalOpcode) {
                case 0xB8: // invokestatic opcode
                    mh = lookup.findStatic(clazz, originalMethodName, originalMethodType);
                    break;
                case 0xB6: // invokevirtual opcode
                case 0xB9: // invokeinterface opcode
                    mh = lookup.findVirtual(clazz, originalMethodName, originalMethodType);
                    break;
                default:
                    throw new BootstrapMethodError();
            }
            mh = mh.asType(callerType);
        } catch (Exception ex) {
            throw new BootstrapMethodError();
        }
        return new ConstantCallSite(mh);
    }
}
