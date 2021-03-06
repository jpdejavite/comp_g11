/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import java.util.*;
import minijava.analysis.*;

@SuppressWarnings("nls")
public final class AClassDecl extends PClassDecl
{
    private PIdentifier _name_;
    private PIdentifier _superClass_;
    private final LinkedList<PVarDecl> _varList_ = new LinkedList<PVarDecl>();
    private final LinkedList<PMethodDecl> _methodList_ = new LinkedList<PMethodDecl>();

    public AClassDecl()
    {
        // Constructor
    }

    public AClassDecl(
        @SuppressWarnings("hiding") PIdentifier _name_,
        @SuppressWarnings("hiding") PIdentifier _superClass_,
        @SuppressWarnings("hiding") List<PVarDecl> _varList_,
        @SuppressWarnings("hiding") List<PMethodDecl> _methodList_)
    {
        // Constructor
        setName(_name_);

        setSuperClass(_superClass_);

        setVarList(_varList_);

        setMethodList(_methodList_);

    }

    @Override
    public Object clone()
    {
        return new AClassDecl(
            cloneNode(this._name_),
            cloneNode(this._superClass_),
            cloneList(this._varList_),
            cloneList(this._methodList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAClassDecl(this);
    }

    public PIdentifier getName()
    {
        return this._name_;
    }

    public void setName(PIdentifier node)
    {
        if(this._name_ != null)
        {
            this._name_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._name_ = node;
    }

    public PIdentifier getSuperClass()
    {
        return this._superClass_;
    }

    public void setSuperClass(PIdentifier node)
    {
        if(this._superClass_ != null)
        {
            this._superClass_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._superClass_ = node;
    }

    public LinkedList<PVarDecl> getVarList()
    {
        return this._varList_;
    }

    public void setVarList(List<PVarDecl> list)
    {
        this._varList_.clear();
        this._varList_.addAll(list);
        for(PVarDecl e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public LinkedList<PMethodDecl> getMethodList()
    {
        return this._methodList_;
    }

    public void setMethodList(List<PMethodDecl> list)
    {
        this._methodList_.clear();
        this._methodList_.addAll(list);
        for(PMethodDecl e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._name_)
            + toString(this._superClass_)
            + toString(this._varList_)
            + toString(this._methodList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._name_ == child)
        {
            this._name_ = null;
            return;
        }

        if(this._superClass_ == child)
        {
            this._superClass_ = null;
            return;
        }

        if(this._varList_.remove(child))
        {
            return;
        }

        if(this._methodList_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._name_ == oldChild)
        {
            setName((PIdentifier) newChild);
            return;
        }

        if(this._superClass_ == oldChild)
        {
            setSuperClass((PIdentifier) newChild);
            return;
        }

        for(ListIterator<PVarDecl> i = this._varList_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PVarDecl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PMethodDecl> i = this._methodList_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PMethodDecl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
