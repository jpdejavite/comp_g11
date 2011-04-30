/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import minijava.analysis.*;

@SuppressWarnings("nls")
public final class ANotExp extends PExp
{
    private PExp _value_;
    private TTokExclamation _token_;

    public ANotExp()
    {
        // Constructor
    }

    public ANotExp(
        @SuppressWarnings("hiding") PExp _value_,
        @SuppressWarnings("hiding") TTokExclamation _token_)
    {
        // Constructor
        setValue(_value_);

        setToken(_token_);

    }

    @Override
    public Object clone()
    {
        return new ANotExp(
            cloneNode(this._value_),
            cloneNode(this._token_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANotExp(this);
    }

    public PExp getValue()
    {
        return this._value_;
    }

    public void setValue(PExp node)
    {
        if(this._value_ != null)
        {
            this._value_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._value_ = node;
    }

    public TTokExclamation getToken()
    {
        return this._token_;
    }

    public void setToken(TTokExclamation node)
    {
        if(this._token_ != null)
        {
            this._token_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._token_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._value_)
            + toString(this._token_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._value_ == child)
        {
            this._value_ = null;
            return;
        }

        if(this._token_ == child)
        {
            this._token_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._value_ == oldChild)
        {
            setValue((PExp) newChild);
            return;
        }

        if(this._token_ == oldChild)
        {
            setToken((TTokExclamation) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}