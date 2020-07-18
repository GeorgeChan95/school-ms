package com.george.school.config;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>
 *     解决ByteSource 序列化问题
 *
 *     整合的时候应该会有人遇到不能序列化的问题吧,
 *     原因是因为用了Shiro的SimpleAuthenticationInfo中的setCredentialsSalt
 *     注入的属性ByteSource没有实现序列化接口，此时呢只用把源码一贴，实现下序列化接口即可X
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 17:18
 * @since JDK 1.8
 */
public class MySimpleByteSource implements ByteSource, Serializable {
    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public MySimpleByteSource() {
    }

    public MySimpleByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public MySimpleByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public MySimpleByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public MySimpleByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }

    public MySimpleByteSource(File file) {
        this.bytes = (new MySimpleByteSource.BytesHelper()).getBytes(file);
    }

    public MySimpleByteSource(InputStream stream) {
        this.bytes = (new MySimpleByteSource.BytesHelper()).getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }

        return this.cachedHex;
    }

    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }

        return this.cachedBase64;
    }

    public String toString() {
        return this.toBase64();
    }

    public int hashCode() {
        return this.bytes != null && this.bytes.length != 0 ? Arrays.hashCode(this.bytes) : 0;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ByteSource) {
            ByteSource bs = (ByteSource) o;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }
}
