package com.ning.lzf4hadoop;

import java.io.IOException;

import com.ning.compress.lzf.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.Decompressor;

public class LzfDecompressor implements Decompressor
{

    public int decompress(byte[] arg0, int arg1, int arg2) throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void end() {
        // TODO Auto-generated method stub
        
    }

    public boolean finished() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean needsDictionary() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean needsInput() {
        // TODO Auto-generated method stub
        return false;
    }
    
    public void reset() {
        // TODO Auto-generated method stub
        
    }

    public void setDictionary(byte[] arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    public void setInput(byte[] arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

}
