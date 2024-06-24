describe('admin-dashboard', () => {
    it('shouldNotLetUnauthenticatedInAdminPanel', () => {
        window.localStorage.setItem('token', '');

        cy.visit('http://localhost:4200/admin')
        cy.contains('Admin Panel').should('not.exist');
    })

    it('shouldNotLetUserInAdminPanel', () => {
        let userToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJEdWNrIFN0dWRpb3MiLCJleHAiOjE3MTkyNzUwMDgsImlhdCI6MTcxOTI1MzQwOCwiZW1haWwiOiJkX2pAbWFpbC5jb20ifQ.IldDQ-9wmHlN8COHYS0_2OVPzJRhcq1DvA3s0gCnVXE";
        window.localStorage.setItem('token', userToken);

        cy.visit('http://localhost:4200/admin')
        cy.contains('Admin Panel').should('not.exist');
    })

    it('shouldLetAdminInPanel', () => {
        let userToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJEdWNrIFN0dWRpb3MiLCJleHAiOjE3MTkyNzUwNzMsImlhdCI6MTcxOTI1MzQ3MywiZW1haWwiOiJib2JAYm9ic2x1eHVyeWVudGVycHJpc2UuY29tIn0.3WT7-oLijvsBbDavBlLPZ8QcwWE56BCL0fEXDqcZ820";
        window.localStorage.setItem('token', userToken);

        cy.visit('http://localhost:4200/admin')
        cy.contains('Admin Panel')
    })

})