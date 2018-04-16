package com.experian.eda.runtime.notes.webapp.rest.notes;

import com.experian.eda.component.upload.count.service.interfaces.business.IUploadCountService;
import com.experian.eda.component.web.configuration.WebConfiguration;
import com.experian.eda.framework.runtime.notes.interfaces.IEDAAttachment;
import com.experian.eda.framework.runtime.notes.interfaces.IEDANote;
import com.experian.eda.framework.runtime.notes.model.EDANote;
import com.experian.eda.framework.runtime.notes.service.interfaces.IRuntimeNoteManagementService;
import com.experian.eda.framework.runtime.notes.service.interfaces.IRuntimeNoteService;
import com.experian.eda.framework.security.interfaces.ISecurityToken;
import com.experian.eda.framework.security.interfaces.SpPermissionKeys;
import com.experian.eda.framework.security.model.internal.Group;
import com.experian.eda.framework.security.model.internal.SecurityPolicy;
import com.experian.eda.framework.security.model.internal.SecurityProfile;
import com.experian.eda.framework.security.model.internal.SpPermission;
import com.experian.eda.framework.security.model.internal.User;
import com.experian.eda.framework.security.service.interfaces.ISecurityService;
import com.experian.eda.framework.security.web.EDAWebSession;
import com.experian.eda.runtime.notes.webapp.CommonUtils;
import com.experian.eda.runtime.notes.webapp.exception.WebServiceSystemException;
import com.experian.eda.runtime.notes.webapp.rest.AbstractRestServiceMock;
import com.experian.eda.runtime.notes.webapp.rest.NoteOperation;
import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.experian.eda.runtime.notes.webapp.rest.TestUtil.setupWicketTester;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Note attachment buildResponse test.
 *
 * This unit test covers file upload scenario.
 *
 * @author Mouli
 * @since 20/3/2018
 */
public class NoteNewAttachBuildResponseTest {
    private AbstractRestServiceMock abstractRestServiceMock;
    private NotesNewAttachmentForBuildResponseTest noteNewAttachment;
    private EDAWebSessionMock edaWebSessionMock;

    private WicketTester wicketTester;

    @Before
    public void before() {
        wicketTester = setupWicketTester();
        abstractRestServiceMock = new AbstractRestServiceMock();
        noteNewAttachment = new NotesNewAttachmentForBuildResponseTest();
        edaWebSessionMock = new EDAWebSessionMock();
    }

    @After
    public void after() {
        abstractRestServiceMock.tearDown();
        edaWebSessionMock.tearDown();
    }

    static class EDAWebSessionMock extends MockUp<EDAWebSession> {
        static EDAWebSession edaWebSession;

        @Mock
        static EDAWebSession get() {
            return edaWebSession;
        }
    }

    class NotesNewAttachmentForBuildResponseTest extends NotesNewAttachment {

        private static final long serialVersionUID = -1465246797529683709L;
        boolean isNoteOperationPermitted;
        ISecurityService securityService;
        IRuntimeNoteService runtimeNoteService;
        IRuntimeNoteManagementService runtimeNoteManagementService;

        @Override
        protected JSON constructJsonResponse() {
            return new JSONObject();
        }

        @Override
        protected boolean isNoteOperationPermitted(IEDANote note, NoteOperation noteOperation) {
            if (noteOperation == NoteOperation.UPDATE) {
                return isNoteOperationPermitted;
            } else {
                return false;
            }
        }

        @Override
        protected IEDANote getNote(String noteId) throws WebServiceSystemException {
            EDANote edaNote = new EDANote();
            edaNote.setTitle("eda-note-title");
            return edaNote;
        }

        @Override
        protected ISecurityService getSecurityService() {
            return securityService;
        }

        @Override
        protected IRuntimeNoteService getRuntimeNoteService() {
            return runtimeNoteService;
        }

        @Override
        protected IRuntimeNoteManagementService getRuntimeNoteManagementService() {
            return runtimeNoteManagementService;
        }
    }

    @Test
    public void testBuildResponse_ShouldReturnValidUploadedFileDetails() throws Exception {
        noteNewAttachment.isNoteOperationPermitted = true;
        noteNewAttachment.securityService = mock(ISecurityService.class);
        noteNewAttachment.runtimeNoteService = mock(IRuntimeNoteService.class);
        noteNewAttachment.runtimeNoteManagementService = mock(IRuntimeNoteManagementService.class);

        EDAWebSessionMock.edaWebSession = mock(EDAWebSession.class);

        IUploadCountService uploadCountService = mock(IUploadCountService.class);
        doNothing().when(uploadCountService).updateUserUploadCount(anyString(), anyString(), any(Date.class), anyInt());

        ISecurityToken iSecurityToken = mock(ISecurityToken.class);
        when(iSecurityToken.getTenantId()).thenReturn("testTenant");
        when(iSecurityToken.getGroup()).thenReturn(mock(Group.class));

        SecurityProfile securityProfile = getMockSecurityProfile();
        when(iSecurityToken.getGroup().getSecurityProfile()).thenReturn(securityProfile);

        SecurityPolicy securityPolicy = mock(SecurityPolicy.class);
        when(securityPolicy.getAcceptedFileExtAttachment()).thenReturn(".txt .jpg");
        when(securityPolicy.getMaximumAttachmentSize()).thenReturn(10000L);

        User user = getMockUser();

        when(EDAWebSessionMock.edaWebSession.getSecurityToken()).thenReturn(iSecurityToken);
        when(EDAWebSessionMock.edaWebSession.getSecurityToken().getUser()).thenReturn(user);
        when(EDAWebSessionMock.edaWebSession.getSecurityToken().getSecurityPolicy()).thenReturn(securityPolicy);
        when(EDAWebSessionMock.edaWebSession.getSecurityToken().getSecurityPolicy().getNumberOfFileUploadPerMinute()).thenReturn(1);

        when(noteNewAttachment.securityService.getUser(anyObject(), anyString())).thenReturn(user);
        when(noteNewAttachment.runtimeNoteService.get(anyObject(), anyString())).thenReturn(noteNewAttachment.getNote("noteId"));
        when(CommonUtils.getSecurityToken()).thenReturn(iSecurityToken);

        String fileName = "sample-file.txt";

        final InputStream iStream = NoteNewAttachBuildResponseTest.class.getClassLoader().getResourceAsStream(fileName);
        int iStreamBytesCount = iStream.available();

        File tmpFile = File.createTempFile("tempFile", "_");
        org.apache.wicket.util.file.File wicketFile = new org.apache.wicket.util.file.File(tmpFile);
        wicketFile.write(iStream);

        wicketTester.getRequest().addFile(fileName, wicketFile, "application/text");
        wicketTester.getRequest().setParameter("qqfile", fileName);

        WebConfiguration webConfiguration = WebConfiguration.get();

        String file = NoteNewAttachBuildResponseTest.class.getClassLoader().getResource("av-scan.properties").getFile();
        File avConfigFile = new File(file);
        webConfiguration.copyAVConfigFile(avConfigFile);

        JSONObject json = (JSONObject) noteNewAttachment.buildResponse();

        assertTrue(iStreamBytesCount > 0);
        assertThat(json.get(IEDAAttachment.FILENAME_KEY), is(equalTo(fileName)));
        assertThat(json.get(IEDAAttachment.FILE_SIZE_KEY), is(equalTo(iStreamBytesCount)));
        assertThat(json.get(IEDAAttachment.FILE_TYPE_KEY), is(equalTo(FilenameUtils.getExtension(fileName))));
    }

    private SecurityProfile getMockSecurityProfile() {
        SecurityProfile securityProfile = mock(SecurityProfile.class);
        Set<SpPermission> spPermissions = new HashSet<>();
        SpPermission spPermission = new SpPermission();
        spPermission.setPermKey(SpPermissionKeys.PERM_NOTE_VIEW);
        spPermissions.add(spPermission);
        when(securityProfile.getPermissions()).thenReturn(spPermissions);
        return securityProfile;
    }

    private User getMockUser() {
        User user = mock(User.class);
        when(user.getLoginName()).thenReturn("testUser");
        when(user.getId()).thenReturn("userId");
        when(user.getLastSuccessfulUpload()).thenReturn(new Date());
        when(user.getLastSuccessfulUploadCount()).thenReturn(0);
        return user;
    }
}

