package com.experian.eda.runtime.notes.webapp.rest.notes

import com.experian.eda.component.upload.count.service.interfaces.business.IUploadCountService
import com.experian.eda.component.web.configuration.WebConfiguration
import com.experian.eda.framework.runtime.notes.interfaces.IEDAAttachment
import com.experian.eda.framework.runtime.notes.interfaces.IEDANote
import com.experian.eda.framework.runtime.notes.model.EDANote
import com.experian.eda.framework.runtime.notes.service.interfaces.IRuntimeNoteManagementService
import com.experian.eda.framework.runtime.notes.service.interfaces.IRuntimeNoteService
import com.experian.eda.framework.security.interfaces.ISecurityToken
import com.experian.eda.framework.security.interfaces.SpPermissionKeys
import com.experian.eda.framework.security.model.internal.Group
import com.experian.eda.framework.security.model.internal.SecurityPolicy
import com.experian.eda.framework.security.model.internal.SecurityProfile
import com.experian.eda.framework.security.model.internal.SpPermission
import com.experian.eda.framework.security.model.internal.User
import com.experian.eda.framework.security.service.exceptions.SecurityServiceException
import com.experian.eda.framework.security.service.interfaces.ISecurityService
import com.experian.eda.framework.security.web.EDAWebSession
import com.experian.eda.runtime.notes.webapp.exception.WebServiceSystemException
import com.experian.eda.runtime.notes.webapp.rest.AbstractRestServiceMock
import com.experian.eda.runtime.notes.webapp.rest.CommonUtilsMock
import com.experian.eda.runtime.notes.webapp.rest.NoteOperation
import mockit.Mock
import mockit.MockUp
import net.sf.json.JSON
import net.sf.json.JSONObject
import org.apache.commons.io.FilenameUtils
import org.apache.wicket.util.tester.WicketTester
import spock.lang.Specification
import spock.lang.Unroll

import static com.experian.eda.runtime.notes.webapp.rest.TestUtil.mockRequestParameter
import static com.experian.eda.runtime.notes.webapp.rest.TestUtil.setupWicketTester
import static org.junit.Assert.assertEquals
/**
 * Notes new attachment test.
 *
 * @author seez
 * @since 6/8/2016.
 */
class NotesNewAttachmentTest extends Specification {
    CommonUtilsMock commonUtilMock
    def abstractRestServiceMock
    NotesNewAttachmentForTest noteNewAttachment
    WicketTester wicketTester
    EDAWebSessionMock edaWebSessionMock

    def setup() {
        wicketTester = setupWicketTester()
        commonUtilMock = new CommonUtilsMock()
        abstractRestServiceMock = new AbstractRestServiceMock()
        noteNewAttachment = new NotesNewAttachmentForTest()
        edaWebSessionMock = new EDAWebSessionMock()
    }

    static class EDAWebSessionMock extends MockUp<EDAWebSession> {
        static EDAWebSession edaWebSession;

        @Mock
        static EDAWebSession get() {
            return edaWebSession;
        }
    }

    class NotesNewAttachmentForTest extends NotesNewAttachment {

        public boolean isNoteOperationPermitted

        ISecurityService securityService
        IRuntimeNoteService runtimeNoteService
        IRuntimeNoteManagementService runtimeNoteManagementService

        @Override
        protected JSON constructJsonResponse() {
            return new JSONObject()
        }

        @Override
        boolean isNoteOperationPermitted(IEDANote note, NoteOperation noteOperation) {
            if (noteOperation == NoteOperation.UPDATE) {
                return isNoteOperationPermitted
            } else {
                return false
            }
        }

        @Override
        protected IEDANote getNote(String noteId) throws WebServiceSystemException {
            EDANote edaNote = new EDANote()
            edaNote.setTitle("eda-note-title")
            return edaNote
        }

        @Override
        protected ISecurityService getSecurityService() {
            return securityService
        }

        @Override
        protected IRuntimeNoteService getRuntimeNoteService() {
            return runtimeNoteService
        }

        @Override
        protected IRuntimeNoteManagementService getRuntimeNoteManagementService() {
            return runtimeNoteManagementService
        }
    }

    def getMockSecurityProfile() {
        SecurityProfile securityProfile = Mock(SecurityProfile.class)
        Set<SpPermission> spPermissions = []
        spPermissions.add(new SpPermission(permKey: SpPermissionKeys.PERM_NOTE_VIEW))
        securityProfile.getPermissions() >> spPermissions
        return securityProfile
    }

    def "buildResponse - should return valid file info"() {
        setup:
        noteNewAttachment.isNoteOperationPermitted = true
        noteNewAttachment.securityService = Mock(ISecurityService.class)
        noteNewAttachment.runtimeNoteService = Mock(IRuntimeNoteService.class)
        noteNewAttachment.runtimeNoteManagementService = Mock(IRuntimeNoteManagementService.class)

        EDAWebSessionMock.edaWebSession = Mock(EDAWebSession.class)

        //IUploadCountService uploadCountService = Mock(IUploadCountService.class)
        //Mock(IUploadCountService.class)

        User user = new User(loginName: "testUser", id: "userId", lastSuccessfulUpload: new Date(), lastSuccessfulUploadCount: 0)
        //SecurityProfile securityProfile = new SecurityProfile(permissions: [new SpPermission(permKey: SpPermissionKeys.PERM_NOTE_VIEW)])
        //SecurityPolicy securityPolicy = new SecurityPolicy(acceptedFileExtAttachment: ".txt .jpg", maximumAttachmentSize: 10000L, numberOfFileUploadPerMinute: 1)

        //ISecurityToken iSecurityToken = Mock(ISecurityToken.class)
        //iSecurityToken.getTenantId() >> "testTenant"
        //iSecurityToken.getGroup() >> Mock(Group.class)
        //iSecurityToken.getSecurityProfile() >> securityProfile

        //EDAWebSessionMock.edaWebSession.getSecurityToken() >> iSecurityToken
        //EDAWebSessionMock.edaWebSession.getSecurityToken().getUser() >> user
        //EDAWebSessionMock.edaWebSession.getSecurityToken().getSecurityPolicy() >> securityPolicy

        noteNewAttachment.securityService.getUser(_,_) >> user
        noteNewAttachment.runtimeNoteService.get(_,_) >> noteNewAttachment.getNote("noteId")

        //CommonUtilsMock.getSecurityToken() >> iSecurityToken

        String fileName = "sample-file.txt"
        final InputStream iStream = NoteNewAttachBuildResponseTest.class.getClassLoader().getResourceAsStream(fileName)
        int iStreamBytesCount = iStream.available()

        File tmpFile = File.createTempFile("tempFile", "_")
        org.apache.wicket.util.file.File wicketFile = new org.apache.wicket.util.file.File(tmpFile)
        wicketFile.write(iStream)

        wicketTester.getRequest().addFile(fileName, wicketFile, "application/text")
        wicketTester.getRequest().setParameter("qqfile", fileName)

        WebConfiguration webConfiguration = WebConfiguration.get()
        String file = NoteNewAttachBuildResponseTest.class.getClassLoader().getResource("av-scan.properties").getFile()
        webConfiguration.copyAVConfigFile(new File(file))

        when:
        JSONObject json = (JSONObject) noteNewAttachment.buildResponse()

        then:
        iStreamBytesCount > 0
        json.get(IEDAAttachment.FILENAME_KEY) == fileName
        json.get(IEDAAttachment.FILE_SIZE_KEY) == iStreamBytesCount
        json.get(IEDAAttachment.FILE_TYPE_KEY) == FilenameUtils.getExtension(fileName)

    }

    @Unroll
    def "isAllowedToAccess - #testName"() {
        setup:
        noteNewAttachment.isNoteOperationPermitted = noteOperationPermitted
        CommonUtilsMock.validNoteTransaction = validNoteTransaction

        when:
        def result = noteNewAttachment.isAllowedToAccess()
        mockRequestParameter(['id': '', 'application_id': '', 'transaction_id': ''])

        then:
        assertEquals(allowAttach, result)

        where:
        testName                                      | noteOperationPermitted | validNoteTransaction   || allowAttach
        'allow edit and valid note transaction'       | true                   | true                   || true
        'allow edit but invalid note transaction'     | true                   | false                  || false
        'not allow edit but valid note transaction'   | false                  | true                   || false
        'not allow edit but invalid note transaction' | false                  | false                  || false
    }

    def cleanup() {
        commonUtilMock.tearDown()
        edaWebSessionMock.tearDown()
    }

    @Unroll
    def "isFileUploadAllowed_#testName"() throws SecurityServiceException {
        setup:
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(new Date())
        calendar.add(Calendar.SECOND, timeDiff)
        Date lastUpload = calendar.getTime()

        User user = Mock()
        user.getLastSuccessfulUpload() >> lastUpload
        user.getLastSuccessfulUploadCount() >> count

        expect:
        isAllow == noteNewAttachment.isFileUploadAllowed(user)

        where:
        testName                                  | timeDiff | count || isAllow
        'after 1 minute'                          | -60      | 3     || true
        'within 1 minute and within upload limit' | -30      | 1     || true
        'within 1 minute and exceed upload limit' | -30      | 3     || false
    }
}
