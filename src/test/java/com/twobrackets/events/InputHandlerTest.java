package com.twobrackets.events;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.twobrackets.ui.Editor;
import com.twobrackets.ui.ScreenHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class InputHandlerTest {
    private InputHandler inputHandler;
    private Editor mockEditor;
    private ScreenHandler mockScreenHandler;

    @BeforeEach
    void setUp() {
        mockScreenHandler = mock(ScreenHandler.class);
        mockEditor = mock(Editor.class);
        when(mockScreenHandler.getEditor()).thenReturn(mockEditor);

        inputHandler = new InputHandler(mockScreenHandler);
    }

    @Test
    void shouldAddCharacterOnCharacterKey() {
        KeyStroke characterKey = new KeyStroke('a', false, false);
        inputHandler.input(characterKey);

        verify(mockEditor).addChar('a');
        verify(mockScreenHandler).refresh(false);
    }

    @Test
    void shouldHandleEnterKey() {
        KeyStroke enterKey = new KeyStroke(KeyType.Enter);
        inputHandler.input(enterKey);

        verify(mockEditor).enter();
        verify(mockScreenHandler).refresh(true);
    }

    @Test
    void shouldHandleBackspaceKey() {
        KeyStroke backspaceKey = new KeyStroke(KeyType.Backspace);
        inputHandler.input(backspaceKey);

        verify(mockEditor).backSpace();
        verify(mockScreenHandler).refresh(true);
    }

    @Test
    void shouldHandleDeleteKey() {
        KeyStroke deleteKey = new KeyStroke(KeyType.Delete);
        inputHandler.input(deleteKey);

        verify(mockEditor).delete();
        verify(mockScreenHandler).refresh(false);
    }

    @Test
    void shouldNavigateUpOnArrowUpKey() {
        KeyStroke arrowUp = new KeyStroke(KeyType.ArrowUp);
        inputHandler.input(arrowUp);

        verify(mockEditor).upp();
        verify(mockScreenHandler).refresh(false);
    }

    @Test
    void shouldNavigateDownOnArrowDownKey() {
        KeyStroke arrowDown = new KeyStroke(KeyType.ArrowDown);
        inputHandler.input(arrowDown);

        verify(mockEditor).down();
        verify(mockScreenHandler).refresh(false);
    }

    @Test
    void shouldNavigateLeftOnArrowLeftKey() {
        KeyStroke arrowLeft = new KeyStroke(KeyType.ArrowLeft);
        inputHandler.input(arrowLeft);

        verify(mockEditor).left();
        verify(mockScreenHandler).refresh(false);
    }

    @Test
    void shouldNavigateRightOnArrowRightKey() {
        KeyStroke arrowRight = new KeyStroke(KeyType.ArrowRight);
        inputHandler.input(arrowRight);

        verify(mockEditor).right();
        verify(mockScreenHandler).refresh(false);
    }

    @Test
    void shouldExitOnEscapeKey() {
        KeyStroke escapeKey = new KeyStroke(KeyType.Escape);
        inputHandler.input(escapeKey);

        verify(mockScreenHandler).exit();
    }
}


