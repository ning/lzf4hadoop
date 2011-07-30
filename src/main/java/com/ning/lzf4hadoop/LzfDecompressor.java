package com.ning.lzf4hadoop;

import java.io.IOException;

import com.ning.compress.lzf.*;

import org.apache.hadoop.io.compress.Decompressor;

public class LzfDecompressor implements Decompressor
{
    protected byte[] _inputBuffer;
    protected int _inputOffset;
    protected int _inputLength;

    // Not optimal (ideally would use segments), but has to do
    
    protected byte[] _decodedBuffer;
    protected int _decodedPtr;
    protected int _decodedEnd;

    protected boolean _finished = false;
    
    protected long _bytesRead;
    protected long _bytesWritten;
    
    public int decompress(byte[] buffer, int offset, int length) throws IOException
    {
        if (buffer == null || offset < 0 || ((offset + length) > buffer.length)) {
            throw new IllegalArgumentException("Bad argument(s) to decompress; buffer "
                    +buffer+", offset "+offset+", length "+length);
        }
        if (_inputBuffer != null) {
            _decodedBuffer = LZFDecoder.decode(_inputBuffer, _inputOffset, _inputLength);
            _decodedPtr = 0;
            _decodedEnd = _decodedBuffer.length;
            _inputBuffer = null;
            _inputOffset = _inputLength = 0;
        }
        if (_decodedBuffer == null || _decodedPtr >= _decodedEnd) {
            return 0;
        }
        int actualLen = Math.min(length, (_decodedEnd - _decodedPtr));
        System.arraycopy(_decodedBuffer, _decodedPtr, buffer, offset, actualLen);
        _bytesWritten += actualLen;
        if ((_decodedPtr += actualLen) >= _decodedEnd) {
            _decodedBuffer = null;
            _decodedPtr = _decodedEnd = 0;
        }
        return actualLen;
    }

    public void end() {
        reset();
    }

    public boolean finished() {
        return _finished;
    }

    public boolean needsDictionary() {
        return false;
    }

    public boolean needsInput() {
        return (_inputBuffer == null) && (_decodedBuffer == null);
    }
    
    public void reset() {
        _finished = false;
        _bytesRead = _bytesWritten = 0L;
        _inputBuffer = null;
        _inputOffset = _inputLength = 0;
        _decodedBuffer = null;
    }

    public void setDictionary(byte[] buffer, int offset, int length) {
        // No-op
    }

    public void setInput(byte[] buffer, int offset, int length)
    {
        // sanity check first:
        if (_inputBuffer != null || _decodedBuffer != null) {
            throw new IllegalStateException("Should not call setInput() before previous input has been fully consumed");
        }
        /* Assumption here is that we need not make a copy; seems sensible as copying
         * adds overhead which is unlikely to be needed with hadoop tasks...
         */
        _bytesRead += length;
        _inputBuffer = buffer;
        _inputOffset = offset;
        _inputLength = length;
    }
}
