package com.ning.lzf4hadoop;

import java.io.*;

import org.junit.Assert;

import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;

public class TestStreams extends Lzf4HadoopTestBase
{
    public void testSimpleRoundtrip() throws Exception
    {
        byte[] input = "Very short test".getBytes("UTF-8");
        LzfCodec codec = new LzfCodec();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        CompressionOutputStream comp = codec.createOutputStream(bytes);
        comp.write(input);
        comp.close();
        
        CompressionInputStream decomp = codec.createInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        bytes = new ByteArrayOutputStream();
        int b;
        
        while ((b = decomp.read()) >= 0) {
            bytes.write(b);
        }
        decomp.close();
        bytes.close();
        
        Assert.assertArrayEquals(input, bytes.toByteArray());
    }
}
