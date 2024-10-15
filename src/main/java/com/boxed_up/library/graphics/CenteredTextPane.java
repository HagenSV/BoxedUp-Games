package com.boxed_up.library.graphics;

import javax.swing.JTextPane;
import javax.swing.text.*;

public class CenteredTextPane extends JTextPane {
    public CenteredTextPane() {

        setEditorKit(new MyEditorKit());
        StyledDocument doc = (StyledDocument) getDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
    }

    private class MyEditorKit extends StyledEditorKit {

        public ViewFactory getViewFactory() {
          return new StyledViewFactory();
        }
      
        static class StyledViewFactory implements ViewFactory {
      
          public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
              if (kind.equals(AbstractDocument.ContentElementName)) {
                return new LabelView(elem);
              } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                return new ParagraphView(elem);
              } else if (kind.equals(AbstractDocument.SectionElementName)) {
                return new CenteredBoxView(elem, View.Y_AXIS);
              } else if (kind.equals(StyleConstants.ComponentElementName)) {
                return new ComponentView(elem);
              } else if (kind.equals(StyleConstants.IconElementName)) {
      
                return new IconView(elem);
              }
            }
      
            return new LabelView(elem);
          }
      
        }
    }
      
    private static class CenteredBoxView extends BoxView {
        public CenteredBoxView(Element elem, int axis) {
          super(elem, axis);
        }
      
        protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
          super.layoutMajorAxis(targetSpan, axis, offsets, spans);
          int textBlockHeight = 0;
          int offset = 0;
      
          for (int i = 0; i < spans.length; i++) {
            textBlockHeight += spans[i];
          }
          offset = (targetSpan - textBlockHeight) / 2;
          for (int i = 0; i < offsets.length; i++) {
            offsets[i] += offset;
          }
      
        }
    }
}