package com.ning.lzf4hadoop;

import java.io.InputStream;
import java.io.IOException;

import org.apache.hadoop.io.compress.CompressionInputStream;

public class LzfDecompressInputStream extends CompressionInputStream
{
    public LzfDecompressInputStream(InputStream in, LzfDecompressor decomp)
    {
        super(in);
    }
    
    @Override
    public int read() throws IOException
    {
        return -1;
    }

    @Override
    public int read(byte[] buffer) throws IOException
    {
        return read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException
    {
        return 0;
    }

    @Override
    public void resetState() throws IOException {
    }
}
