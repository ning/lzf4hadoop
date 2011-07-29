package com.ning.lzf4hadoop;

import java.io.*;

import org.apache.hadoop.io.compress.CompressionOutputStream;

import com.ning.compress.lzf.LZFOutputStream;

public class LzfCompressOutputStream extends CompressionOutputStream
{
    protected LZFOutputStream lzfOut;
    
    public LzfCompressOutputStream(OutputStream out)
    {
        super(out);
        lzfOut = new LZFOutputStream(out);
    }

    @Override
    public void close() throws IOException {
        lzfOut.close();
    }

    @Override
    public void finish() throws IOException {
        lzfOut.finishBlock();
        lzfOut.flush();
    }

    @Override
    public void flush() throws IOException {
        lzfOut.flush();
    }
    
    @Override
    public void resetState() throws IOException {
        // What, if anything, should we do here?
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        lzfOut.write(buffer, offset, length);
    }

    @Override
    public void write(int b) throws IOException {
        lzfOut.write(b);
    }

}
