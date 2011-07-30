package com.ning.lzf4hadoop;

import java.io.*;
import java.util.Arrays;

import org.apache.hadoop.io.compress.Decompressor;
import org.junit.Assert;

import com.ning.compress.lzf.LZFEncoder;

public class TestDecompressor extends Lzf4HadoopTestBase
{
    public void testSingleCompress() throws Exception
    {
        byte[] orig = "Another trivial test".getBytes("UTF-8");
        byte[] compressed = LZFEncoder.encode(orig);
        Decompressor decomp = new LzfCodec().createDecompressor();
        decomp.setInput(compressed, 0, compressed.length);

        byte[] result = new byte[100];
        int len = decomp.decompress(result, 0, 100);
        result = Arrays.copyOf(result, len);
        
        Assert.assertArrayEquals(orig, result);
    }

    public void testSequence() throws Exception
    {
        byte[] input1 = "Start with one piece......".getBytes("UTF-8");
        byte[] input2 = " then another one, with repepepepepepetion".getBytes("UTF-8");
        byte[] input3 = "and that's it!".getBytes("UTF-8");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(input1);
        out.write(input2);
        out.write(input3);
        byte[] input = out.toByteArray();

        out = new ByteArrayOutputStream();
        out.write(LZFEncoder.encode(input1));
        out.write(LZFEncoder.encode(input2));
        out.write(LZFEncoder.encode(input3));
        byte[] compressed = out.toByteArray();

        byte[] result = new byte[200];

        Decompressor decomp = new LzfCodec().createDecompressor();
        decomp.setInput(compressed, 0, compressed.length);
        int len = decomp.decompress(result, 0, result.length);
        result = Arrays.copyOf(result, len);

        Assert.assertArrayEquals(input, result);
    }
}
