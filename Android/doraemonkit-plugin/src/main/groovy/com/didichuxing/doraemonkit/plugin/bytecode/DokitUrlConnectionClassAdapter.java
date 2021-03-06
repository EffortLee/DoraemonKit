package com.didichuxing.doraemonkit.plugin.bytecode;

import com.android.build.gradle.AppExtension;
import com.didichuxing.doraemonkit.plugin.DokitExtension;
import com.didichuxing.doraemonkit.plugin.StringUtils;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.AmapLocationMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.ApplicationOnCreateMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.BaiduLocationMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.FlagMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.OkHttpMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.PlatformHttpMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.TencentLocationMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.comm.TencentLocationSingleMethodAdapter;
import com.didichuxing.doraemonkit.plugin.bytecode.method.urlconnection.UrlConnectionMethodAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Created by jint on 13/12/2019.
 * 类访问器
 */
public final class DokitUrlConnectionClassAdapter extends ClassVisitor {

    private DokitExtension dokitExtension;


    /**
     *
     * @param cv cv 传进来的是 ClassWriter
     * @param appExtension appExtension
     * @param dokitExtension dokitExtension
     */
    public DokitUrlConnectionClassAdapter(final ClassVisitor cv, AppExtension appExtension, DokitExtension dokitExtension) {
        super(Opcodes.ASM7, cv);
        this.dokitExtension = dokitExtension;

    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);

    }


    /**
     * Visits a method of the class. This method <i>must</i> return a new {@link MethodVisitor}
     * instance (or {@literal null}) each time it is called, i.e., it should not return a previously
     * returned visitor.
     *
     * @param access     the method's access flags (see {@link Opcodes}). This parameter also indicates if
     *                   the method is synthetic and/or deprecated.
     * @param methodName the method's name.
     * @param desc       the method's descriptor (see {@link Type}).
     * @param signature  the method's signature. May be {@literal null} if the method parameters,
     *                   return type and exceptions do not use generic types.
     * @param exceptions the internal names of the method's exception classes (see {@link
     *                   Type#getInternalName()}). May be {@literal null}.
     * @return an object to visit the byte code of the method, or {@literal null} if this class
     * visitor is not interested in visiting the code of this method.
     */
    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        //从传进来的ClassWriter中读取MethodVisitor
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        //开关被关闭 不插入代码
        if (!dokitExtension.dokitPluginSwitch) {
            return mv;
        }
        //过滤所有类中当前方法中所有的字节码
        return mv == null ? null : new UrlConnectionMethodAdapter(access, desc, mv);

    }


}