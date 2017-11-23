package sample;

import com.sun.jna.*;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;
import net.tec.kyfw.util.n;
import net.tec.kyfw.util.u;

public abstract interface Header extends Library {
    public static final a b = new a(-2147483648L);
    public static final a c = new a(-2147483647L);
    public static final a d = new a(-2147483646L);
    public static final a e = new a(-2147483645L);
    public static final a f = new a(-2147483644L);
    public static final a g = new a(-2147483568L);
    public static final a h = new a(-2147483552L);
    public static final a i = new a(-2147483643L);
    public static final a j = new a(-2147483642L);
    public static final Header a = (Header) Native.loadLibrary("Advapi32.dll", Header.class, W32APIOptions.ASCII_OPTIONS);

    public abstract int RegOpenKeyEx(a parama, String paramString, int paramInt1, int paramInt2, b paramb);

    public abstract int RegOpenKey(a parama, String paramString, b paramb);

    public abstract int RegCloseKey(a parama);

    public abstract int RegGetValue(a parama, String paramString1, String paramString2, int paramInt, IntByReference paramIntByReference1, byte[] paramArrayOfByte, IntByReference paramIntByReference2);

    public abstract int RegCreateKey(a parama, String paramString, b paramb);

    public abstract int RegSetKeyValue(a parama, String paramString1, String paramString2, int paramInt1, byte[] paramArrayOfByte, int paramInt2);

    public abstract int RegSetValue(a parama, String paramString, int paramInt1, byte[] paramArrayOfByte, int paramInt2);

    public abstract int RegSetValueEx(a parama, String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

    public abstract int RegDeleteKey(a parama, String paramString);
    public abstract int RegQueryValueEx(a parama, String paramString, Integer paramInteger, IntByReference paramIntByReference1, byte[] paramArrayOfByte, IntByReference paramIntByReference2);

    public static class a
            extends PointerType {
        private boolean immutable;

        public a() {
        }

        public a(Pointer paramPointer) {
            setPointer(paramPointer);
            this.immutable = true;
        }

        public a(long paramLong) {
            setPointer(new Pointer(paramLong));
            this.immutable = true;
        }

        public Object fromNative(Object paramObject, FromNativeContext paramFromNativeContext) {
            Object localObject = super.fromNative(paramObject, paramFromNativeContext);
            Pointer localPointer = Pointer.createConstant(Pointer.SIZE == 8 ? -1L : 4294967295L);
            if (localPointer.equals(localObject)) {
                return localPointer;
            }
            return localObject;
        }

        public void setPointer(Pointer paramPointer) {
            if (this.immutable) {
                throw new UnsupportedOperationException("immutable reference");
            }
            super.setPointer(paramPointer);
        }
    }
    public static class c
    {
        public static String a(a parama, String paramString1, String paramString2)
        {
            String str = null;
            IntByReference localIntByReference1 = new IntByReference();
            IntByReference localIntByReference2 = new IntByReference();
            Header.b localb = new b();
            int i = Header.a.RegOpenKeyEx(parama, paramString1, 0, 131097, localb);
            if (i == 0)
            {
                i = Header.a.RegQueryValueEx(localb.getValue(), paramString2, Integer.valueOf(0), localIntByReference2, (byte[])null, localIntByReference1);
                if (i == 0)
                {
                    byte[] arrayOfByte = new byte[localIntByReference1.getValue()];
                    if (n.a())
                    {
                        IntByReference localIntByReference3 = new IntByReference(arrayOfByte.length);
                        IntByReference localIntByReference4 = new IntByReference();
                        i = Header.a.RegGetValue(localb.getValue(), null, paramString2, 65535, localIntByReference4, arrayOfByte, localIntByReference3);
                    }
                    else
                    {
                        i = Header.a.RegQueryValueEx(localb.getValue(), paramString2, Integer.valueOf(0), localIntByReference2, arrayOfByte, localIntByReference1);
                    }
                    if (i == 0) {
                        str = Native.toString(arrayOfByte);
                    }
                }
            }
            Header.a.RegCloseKey(localb.getValue());
            return str;
        }

        public static boolean a(Header.a parama, String paramString1, String paramString2, String paramString3)
        {
            int i = -1;
            byte[] arrayOfByte = Native.toByteArray(paramString3);
            if (n.a())
            {
                i = Header.a.RegSetKeyValue(parama, paramString1, paramString2, 1, arrayOfByte, arrayOfByte.length);
            }
            else
            {
                Header.b localb = new Header.b();
                i = Header.a.RegOpenKeyEx(parama, paramString1, 0, 131103, localb);
                if (i == 2) {
                    Header.a.RegCreateKey(parama, paramString1, localb);
                }
                i = Header.a.RegSetValueEx(localb.getValue(), paramString2, 0, 1, arrayOfByte, arrayOfByte.length);
                Header.a.RegCloseKey(localb.getValue());
            }
            return i == 0;
        }

    }
    public static class b
            extends ByReference
    {
        public b()
        {
            this(null);
        }

        public b(Header.a parama)
        {
            super(1243124);
//            super();
            setValue(parama);
        }

        public void setValue(Header.a parama)
        {
            getPointer().setPointer(0L, parama != null ? parama.getPointer() : null);
        }

        public Header.a getValue()
        {
            Pointer localPointer1 = getPointer().getPointer(0L);
            if (localPointer1 == null) {
                return null;
            }
            Pointer localPointer2 = Pointer.createConstant(Pointer.SIZE == 8 ? -1L : 4294967295L);
            if (localPointer2.equals(localPointer1)) {
                return new Header.a(localPointer2);
            }
            return new Header.a(localPointer1);
        }
    }
}

