/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import minijava.analysis.*;

@SuppressWarnings("nls")
public final class TTokPublic extends Token
{
    public TTokPublic()
    {
        super.setText("public");
    }

    public TTokPublic(int line, int pos)
    {
        super.setText("public");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTokPublic(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTokPublic(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTokPublic text.");
    }
}
