package org.gwtxtend.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.gwtxtend.annotations.GwtModule;

@GwtModule(moduleName = "GwtXtendExample")
@SuppressWarnings("all")
public class GwtXtendExample implements EntryPoint {
  private TextBox nameField;
  
  private Button sendButton;
  
  private Label errorLabel;
  
  private DialogBox dialogBox;
  
  private Label textToServerLabel;
  
  private HTML serverResponseLabel;
  
  private Button closeButton;
  
  public void onModuleLoad() {
    this.createNeccessaryWidgets();
    this.focusOnNameField();
  }
  
  private void createNeccessaryWidgets() {
    this.createNameField();
    this.createSendButton();
    this.createErrorLabel();
    this.createDialogBox();
  }
  
  private void createNameField() {
    TextBox _textBox = new TextBox();
    final Procedure1<TextBox> _function = new Procedure1<TextBox>() {
      public void apply(final TextBox it) {
        it.setText("Insert your name");
        final KeyUpHandler _function = new KeyUpHandler() {
          public void onKeyUp(final KeyUpEvent it) {
            int _nativeKeyCode = it.getNativeKeyCode();
            boolean _equals = (_nativeKeyCode == KeyCodes.KEY_ENTER);
            if (_equals) {
              GwtXtendExample.this.sendNameToServer();
            }
          }
        };
        it.addKeyUpHandler(_function);
      }
    };
    TextBox _doubleArrow = ObjectExtensions.<TextBox>operator_doubleArrow(_textBox, _function);
    this.nameField = _doubleArrow;
    RootPanel _get = RootPanel.get("nameFieldContainer");
    _get.add(this.nameField);
  }
  
  private void createSendButton() {
    Button _button = new Button("Send");
    final Procedure1<Button> _function = new Procedure1<Button>() {
      public void apply(final Button it) {
        it.addStyleName("sendButton");
        final ClickHandler _function = new ClickHandler() {
          public void onClick(final ClickEvent it) {
            GwtXtendExample.this.sendNameToServer();
          }
        };
        it.addClickHandler(_function);
      }
    };
    Button _doubleArrow = ObjectExtensions.<Button>operator_doubleArrow(_button, _function);
    this.sendButton = _doubleArrow;
    RootPanel _get = RootPanel.get("sendButtonContainer");
    _get.add(this.sendButton);
  }
  
  private void createErrorLabel() {
    Label _label = new Label();
    this.errorLabel = _label;
    RootPanel _get = RootPanel.get("errorLabelContainer");
    _get.add(this.errorLabel);
  }
  
  private void createDialogBox() {
    Label _label = new Label();
    this.textToServerLabel = _label;
    HTML _hTML = new HTML();
    this.serverResponseLabel = _hTML;
    Button _button = new Button("Close");
    final Procedure1<Button> _function = new Procedure1<Button>() {
      public void apply(final Button it) {
        Element _element = it.getElement();
        _element.setId("closeButton");
        final ClickHandler _function = new ClickHandler() {
          public void onClick(final ClickEvent it) {
            GwtXtendExample.this.dialogBox.hide();
            GwtXtendExample.this.sendButton.setEnabled(true);
            GwtXtendExample.this.sendButton.setFocus(true);
          }
        };
        it.addClickHandler(_function);
      }
    };
    Button _doubleArrow = ObjectExtensions.<Button>operator_doubleArrow(_button, _function);
    this.closeButton = _doubleArrow;
    DialogBox _dialogBox = new DialogBox();
    final Procedure1<DialogBox> _function_1 = new Procedure1<DialogBox>() {
      public void apply(final DialogBox it) {
        it.setText("Remote Procedure Call");
        it.setAnimationEnabled(true);
        VerticalPanel _verticalPanel = new VerticalPanel();
        final Procedure1<VerticalPanel> _function = new Procedure1<VerticalPanel>() {
          public void apply(final VerticalPanel it) {
            it.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
            it.addStyleName("dialogVPanel");
            HTML _hTML = new HTML("<b>Sending name to the server:</b>");
            it.add(_hTML);
            it.add(GwtXtendExample.this.textToServerLabel);
            HTML _hTML_1 = new HTML("<br><b>Server replies: </b>");
            it.add(_hTML_1);
            it.add(GwtXtendExample.this.serverResponseLabel);
            it.add(GwtXtendExample.this.closeButton);
          }
        };
        VerticalPanel _doubleArrow = ObjectExtensions.<VerticalPanel>operator_doubleArrow(_verticalPanel, _function);
        it.setWidget(_doubleArrow);
      }
    };
    DialogBox _doubleArrow_1 = ObjectExtensions.<DialogBox>operator_doubleArrow(_dialogBox, _function_1);
    this.dialogBox = _doubleArrow_1;
  }
  
  private void focusOnNameField() {
    this.nameField.setFocus(true);
    this.nameField.selectAll();
  }
  
  private void sendNameToServer() {
    this.errorLabel.setText("");
    final String name = this.nameField.getText();
    this.sendButton.setEnabled(false);
    this.textToServerLabel.setText(name);
    this.serverResponseLabel.setText("");
  }
}
