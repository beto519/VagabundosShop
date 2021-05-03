package entidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Carlos Soto Pacheco
 * @author Jesus Alberto Rivera Lopez
 * @author Raul Ernesto Molina Lizarraga
 * @author Sebastian Santana
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * La clase TextPrompt mostrará un mensaje sobre la parte superior de un componente de texto cuando
 * el documento del campo de texto está vacío. La propiedad Show se usa para
 * determinar la visibilidad de la solicitud.
 *
 * La fuente y el color de primer plano de la solicitud tendrán por defecto esas propiedades
 * del componente de texto primario. Usted es libre de cambiar las propiedades después de
 * construccion de clase.
 */
public class TextPrompt extends JLabel implements FocusListener, DocumentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Show {
		ALWAYS, FOCUS_GAINED, FOCUS_LOST;
	}

	private JTextComponent component;
	private Document document;

	private Show show;
	private boolean showPromptOnce;
	private int focusLost;
/**
 * 
 * @param text
 * @param component 
 */
	public TextPrompt(String text, JTextComponent component) {
		this(text, component, Show.ALWAYS);
	}
/**
 * 
 * @param text
 * @param component
 * @param show 
 */
	public TextPrompt(String text, JTextComponent component, Show show) {
		this.component = component;
		setShow(show);
		document = component.getDocument();

		setText(text);
		setFont(component.getFont());
                
		setForeground(Color.gray);
		setHorizontalAlignment(JLabel.LEADING);

		component.addFocusListener(this);
		document.addDocumentListener(this);

		component.setLayout(new BorderLayout());
		component.add(this);
		checkForPrompt();
	}

	/**
         * Método de conveniencia para cambiar el valor alfa del primer plano actual
         * 
         * Color al valor de especificación.
	 *
	 * @param alpha
	 *          valor en el rango de 0 - 1.0.
	 */
	public void changeAlpha(float alpha) {
		changeAlpha((int) (alpha * 255));
	}

	/**
	 
        * Método de conveniencia para cambiar el valor alfa del primer plano actual
        * 
        * Color al valor de especificación.
	 *
	 * @param alpha
	 *            
         *  valor en el rango de 0 a 255.
	 */
	public void changeAlpha(int alpha) {
		alpha = alpha > 255 ? 255 : alpha < 0 ? 0 : alpha;

		Color foreground = getForeground();
		int red = foreground.getRed();
		int green = foreground.getGreen();
		int blue = foreground.getBlue();

		Color withAlpha = new Color(red, green, blue, alpha);
		super.setForeground(withAlpha);
	}

	/**
	
        * Método conveniente para cambiar el estilo de la fuente actual. El estilo
        * los valores se encuentran en la clase Font. Los valores comunes pueden ser: Font.BOLD,
        * Font.ITALIC y Font.BOLD + Font.ITALIC.
	 *
	 * @param style
	 *            valor que representa el nuevo estilo de la fuente.
	 */
	public void changeStyle(int style) {
		setFont(getFont().deriveFont(style));
	}

	/**
	 * 
         *  Obtenga la propiedad Show
	 *
	 * @return the Show property.
	 */
	public Show getShow() {
		return show;
	}

	/**
	
        * Establezca la propiedad Show prompt para controlar cuándo se muestra la solicitud. Válido
        * los valores son:
        * 
        * Show.AWLAYS (predeterminado): muestra siempre el mensaje Show.Focus_GAINED - show
        * el aviso cuando el componente se enfoca (y oculta el aviso cuando se enfoca
        * se pierde) Show.Focus_LOST - muestra el mensaje cuando el componente pierde el foco
        * (y ocultar el mensaje cuando se gana el foco)
	 *
	 * @param show
	 *            
	 */
	public void setShow(Show show) {
		this.show = show;
	}

	/**
	 * 
        Obtenga la propiedad showPromptOnce
	 *
	 * @return la  propiedad showPromptOnce.
	 */
	public boolean getShowPromptOnce() {
		return showPromptOnce;
	}

	/**
	 
        * Mostrar el aviso una vez. Una vez que el componente ha ganado / perdido el foco una vez, el
        * prompt no se mostrará de nuevo.
	 *
	 * @param showPromptOnce
	 *            
         *       cuando es verdadero, el mensaje solo se mostrará una vez, de lo contrario
         *       se mostrará repetidamente.
	 */
	public void setShowPromptOnce(boolean showPromptOnce) {
		this.showPromptOnce = showPromptOnce;
	}

	/**
	 
        * Compruebe si el mensaje debe ser visible o no. La visibilidad
        * cambio en las actualizaciones del documento y en los cambios de foco.
	 */
	private void checkForPrompt() {
		
                // Se ha ingresado texto, elimine la solicitud

		if (document.getLength() > 0) {
			setVisible(false);
			return;
		}

		
                // El mensaje ya se ha mostrado una vez, elimínalo

		if (showPromptOnce && focusLost > 0) {
			setVisible(false);
			return;
		}

		
                    // Verifique la propiedad Mostrar y el foco del componente para determinar si el
                    // se debe mostrar la solicitud.
		if (component.hasFocus()) {
			if (show == Show.ALWAYS || show == Show.FOCUS_GAINED)
				setVisible(true);
			else
				setVisible(false);
		} else {
			if (show == Show.ALWAYS || show == Show.FOCUS_LOST)
				setVisible(true);
			else
				setVisible(false);
		}
	}

	
            // Implementar FocusListener
/**
 * 
 * @param e 
 */
	public void focusGained(FocusEvent e) {
		checkForPrompt();
	}
/**
 * 
 * @param e 
 */
	public void focusLost(FocusEvent e) {
		focusLost++;
		checkForPrompt();
	}

	// Implement DocumentListener
/**
 * 
 * @param e 
 */
	public void insertUpdate(DocumentEvent e) {
		checkForPrompt();
	}
/**
 * 
 * @param e 
 */
	public void removeUpdate(DocumentEvent e) {
		checkForPrompt();
	}
/**
 * 
 * @param e 
 */
	public void changedUpdate(DocumentEvent e) {
	}
}