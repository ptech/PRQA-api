/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;
import net.praqma.prqa.products.QAV;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class QAVTest {
    @Test public void testQAVContruction() {
        QAV qav = new QAV();
        
        assertFalse(qav.isUseSingleSnapshotMode());
        assertNull(qav.getPort());
        assertNull(qav.getUser());
        assertNull(qav.getPassword());
        assertNull(qav.getSourceTopLevelDir());
    }
}
