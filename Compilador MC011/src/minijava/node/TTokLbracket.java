/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import minijava.analysis.*;

@SuppressWarnings("nls")
public final class TTokLbracket extends Token
{
    public TTokLbracket()
    {
        super.setText("[");
    }

    public TTokLbracket(int line, int pos)
    {
        super.setText("[");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTokLbracket(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTokLbracket(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTokLbracket text.");
    }
}
