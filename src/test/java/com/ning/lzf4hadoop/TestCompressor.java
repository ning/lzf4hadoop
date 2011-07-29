package com.ning.lzf4hadoop;

import java.io.*;
import java.util.Arrays;

import org.apache.hadoop.io.compress.Compressor;
import org.junit.Assert;

import com.ning.compress.lzf.LZFEncoder;

public class TestCompressor extends Lzf4HadoopTestBase
{
    public void testSingleCompress() throws Exception
    {
        byte[] input = "Another trivial test".getBytes("UTF-8");
        Compressor comp = new LzfCodec().createCompressor();
        comp.setInput(input, 0, input.length);
        comp.finish();

        byte[] result = new byte[100];
        int len = comp.compress(result, 0, 100);
        result = Arrays.copyOf(result, len);

        byte[] exp = LZFEncoder.encode(input);
        
        Assert.assertArrayEquals(exp, result);
    }

    public void testCompressSeq() throws Exception
    {
        byte[] input1 = "Start with one piece......".getBytes("UTF-8");
        byte[] input2 = " then another one, with repepepepepepetion".getBytes("UTF-8");
        byte[] input3 = "and that's it!".getBytes("UTF-8");

        Compressor comp = new LzfCodec().createCompressor();

        byte[] buffer = new byte[100];
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        int count;

        // we know following works with lzf codec, simplified:
        comp.setInput(input1, 0, input1.length);
        count = comp.compress(buffer, 0, 100);
        bytes.write(buffer, 0, count);
        comp.setInput(input2, 0, input2.length);
        count = comp.compress(buffer, 0, 100);
        bytes.write(buffer, 0, count);
        comp.setInput(input3, 0, input3.length);
        comp.finish();
        count = comp.compress(buffer, 0, 100);
        bytes.write(buffer, 0, count);

        byte[] act = bytes.toByteArray();

        // can't encode as one chunk, need to do one by one here as well
        bytes = new ByteArrayOutputStream();
        bytes.write(LZFEncoder.encode(input1));
        bytes.write(LZFEncoder.encode(input2));
        bytes.write(LZFEncoder.encode(input3));
        
        byte[] exp = bytes.toByteArray();
        
        Assert.assertArrayEquals(exp, act);
    }
}
