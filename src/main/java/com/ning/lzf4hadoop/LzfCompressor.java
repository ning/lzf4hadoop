package com.ning.lzf4hadoop;

import java.io.IOException;

import com.ning.compress.lzf.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.Compressor;

public class LzfCompressor implements Compressor
{
    protected byte[] _inputBuffer;
    protected int _inputPtr;
    protected int _inputEnd;

    protected long _bytesRead;
    protected long _bytesWritten;

    public LzfCompressor() {
    }
    
    public int compress(byte[] buffer, int offset, int length) throws IOException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public void end() {
    }

    public void finish() {
        // TODO Auto-generated method stub
        
    }

    public boolean finished() {
        // TODO Auto-generated method stub
        return false;
    }

    public long getBytesRead() {
        return _bytesRead;
    }

    public long getBytesWritten() {
        return _bytesWritten;
    }

    public boolean needsInput() {
        return (_inputPtr >= _inputEnd);
    }

    /* Added in Hadoop 0.21.0... let's implement, but can't yet
     * mark with @Override
     */
//    @Override
    public synchronized void reinit(Configuration conf) {
        reset();
    }
    
    public void reset()
    {
        _bytesRead = _bytesWritten = 0L;
        _inputBuffer = null;
        _inputPtr = _inputEnd = 0;
    }

    public void setDictionary(byte[] buffer, int offset, int length) {
        // No-op
    }
    
    public void setInput(byte[] buffer, int offset, int length)
    {
        // sanity check first:
        if (_inputPtr < _inputEnd) {
            throw new IllegalStateException("Should not call setInput() before input is needed");
        }
        /* Assumption here is that we need not make a copy; seems sensible as copying
         * adds overhead which is unlikely to be needed with hadoop tasks...
         */
        _bytesRead += length;
        _inputBuffer = buffer;
        _inputPtr = offset;
        _inputEnd = offset + length;
    }

}
