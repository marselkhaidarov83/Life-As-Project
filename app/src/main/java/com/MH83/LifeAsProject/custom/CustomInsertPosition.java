package com.MH83.LifeAsProject.custom;

public class CustomInsertPosition
{
    private int _insertPosition;
    public int get_insertPosition() { return _insertPosition; }

    public void set_insertPosition(int insertPosition) { _insertPosition = insertPosition; }

    private int _start;
    public int get_start() { return _start; }
    public void set_start(int start) { _start = start; }

    private int _end;
    public int get_end() { return _end;}
    public void set_end(int end) { _end = end; }

    public CustomInsertPosition(int insertPosition, int start, int end)
    {
        _insertPosition = insertPosition;
        _start = start;
        _end = end;
    }

    public CustomInsertPosition Clone()
    {
        return new CustomInsertPosition(_insertPosition, _start, _end);
    }
}
