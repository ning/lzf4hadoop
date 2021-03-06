package com.ning.lzf4hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.Compressor;
import org.apache.hadoop.io.compress.Decompressor;

public class LzfCodec extends Configured implements CompressionCodec
{
    public Compressor createCompressor() {
        return new LzfCompressor();
    }

    public Decompressor createDecompressor() {
        return new LzfDecompressor();
    }

    public CompressionInputStream createInputStream(InputStream in) throws IOException {
        return new LzfDecompressInputStream(in);
    }

    public CompressionInputStream createInputStream(InputStream in, Decompressor decomp) throws IOException {
        return createInputStream(in);
    }

    public CompressionOutputStream createOutputStream(OutputStream out) throws IOException {
        return new LzfCompressOutputStream(out);
    }

    public CompressionOutputStream createOutputStream(OutputStream out, Compressor comp) throws IOException {
        return createOutputStream(out);
    }

    public Class<? extends Compressor> getCompressorType() {
        return LzfCompressor.class;
    }

    public Class<? extends Decompressor> getDecompressorType() {
        return LzfDecompressor.class;
    }

    public String getDefaultExtension() {
        return ".lzf";
    }

    @Override
    public void setConf(Configuration conf)
    {
        super.setConf(conf);
        // anything we need to do here?
    }
}
