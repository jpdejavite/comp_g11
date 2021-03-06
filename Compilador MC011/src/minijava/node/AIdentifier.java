/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import minijava.analysis.*;

@SuppressWarnings("nls")
public final class AIdentifier extends PIdentifier
{
    private TTokId _s_;

    public AIdentifier()
    {
        // Constructor
    }

    public AIdentifier(
        @SuppressWarnings("hiding") TTokId _s_)
    {
        // Constructor
        setS(_s_);

    }

    @Override
    public Object clone()
    {
        return new AIdentifier(
            cloneNode(this._s_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIdentifier(this);
    }

    public TTokId getS()
    {
        return this._s_;
    }

    public void setS(TTokId node)
    {
        if(this._s_ != null)
        {
            this._s_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._s_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._s_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._s_ == child)
        {
            this._s_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._s_ == oldChild)
        {
            setS((TTokId) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
