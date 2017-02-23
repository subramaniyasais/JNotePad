
package jnotepad;
/* PrintProperties.java*/
import java.awt.*;
import java.awt.print.*;
class pagePainter implements Printable
  {
    public int print(Graphics g, PageFormat pf,int pageIndex)
    // A demo method implementation: does not do any printing
           {
             if (pageIndex>1)return NO_SUCH_PAGE;
          g.drawString("Printed",100,100);
            /*put something on the paper*/
            return Printable.PAGE_EXISTS;
           }
  }