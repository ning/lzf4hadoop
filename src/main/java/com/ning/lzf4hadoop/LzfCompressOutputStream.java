package com.ning.lzf4hadoop;

import java.io.*;

import org.apache.hadoop.io.compress.CompressionOutputStream;

public class LzfCompressOutputStream extends CompressionOutputStream
{
    public LzfCompressOutputStream(OutputStream out, LzfCompressor comp)
    {
        super(out);
    }

    @Override
    public void finish() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetState() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void write(byte[] arg0, int arg1, int arg2) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void write(int arg0) throws IOException {
        // TODO Auto-generated method stub
        
    }

}
