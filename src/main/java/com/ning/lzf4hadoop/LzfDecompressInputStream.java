package com.ning.lzf4hadoop;

import java.io.InputStream;
import java.io.IOException;

import org.apache.hadoop.io.compress.*;

import com.ning.compress.lzf.LZFInputStream;

public class LzfDecompressInputStream
    extends CompressionInputStream
{
    protected LZFInputStream lzfInput;
    
    public LzfDecompressInputStream(InputStream in) throws IOException
    {
        super(in);
        lzfInput = new LZFInputStream(in);
    }

    @Override
    public int available() throws IOException {
        return lzfInput.available();
    }
    
    @Override
    public void close() throws IOException {
        lzfInput.close();
    }
    
    @Override
    public int read() throws IOException
    {
        return lzfInput.read();
    }

    @Override
    public int read(byte[] buffer) throws IOException
    {
        return read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException
    {
        return lzfInput.read(buffer, offset, length);
    }

    @Override
    public void resetState() throws IOException {
        lzfInput.discardBuffered();
    }

    @Override
    public long skip(long n) throws IOException {
        return lzfInput.skip(n);
    }
}
