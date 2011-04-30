/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import minijava.analysis.*;

@SuppressWarnings("nls")
public final class AMainClass extends PMainClass
{
    private PIdentifier _className_;
    private PIdentifier _mainArgName_;
    private PStatement _s_;

    public AMainClass()
    {
        // Constructor
    }

    public AMainClass(
        @SuppressWarnings("hiding") PIdentifier _className_,
        @SuppressWarnings("hiding") PIdentifier _mainArgName_,
        @SuppressWarnings("hiding") PStatement _s_)
    {
        // Constructor
        setClassName(_className_);

        setMainArgName(_mainArgName_);

        setS(_s_);

    }

    @Override
    public Object clone()
    {
        return new AMainClass(
            cloneNode(this._className_),
            cloneNode(this._mainArgName_),
            cloneNode(this._s_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMainClass(this);
    }

    public PIdentifier getClassName()
    {
        return this._className_;
    }

    public void setClassName(PIdentifier node)
    {
        if(this._className_ != null)
        {
            this._className_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._className_ = node;
    }

    public PIdentifier getMainArgName()
    {
        return this._mainArgName_;
    }

    public void setMainArgName(PIdentifier node)
    {
        if(this._mainArgName_ != null)
        {
            this._mainArgName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._mainArgName_ = node;
    }

    public PStatement getS()
    {
        return this._s_;
    }

    public void setS(PStatement node)
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
            + toString(this._className_)
            + toString(this._mainArgName_)
            + toString(this._s_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._className_ == child)
        {
            this._className_ = null;
            return;
        }

        if(this._mainArgName_ == child)
        {
            this._mainArgName_ = null;
            return;
        }

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
        if(this._className_ == oldChild)
        {
            setClassName((PIdentifier) newChild);
            return;
        }

        if(this._mainArgName_ == oldChild)
        {
            setMainArgName((PIdentifier) newChild);
            return;
        }

        if(this._s_ == oldChild)
        {
            setS((PStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
