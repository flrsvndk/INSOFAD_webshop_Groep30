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
        let userToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJIb3Jpem9uIERldmVsb3BtZW50cyIsImV4cCI6MTcxOTI4NzcwMCwiaWF0IjoxNzE5MjY2MTAwLCJlbWFpbCI6ImJvYkBib2JzbHV4dXJ5ZW50ZXJwcmlzZS5jb20ifQ.93eHhp575onquMcP2PPDHay8Ib-2XWrVE3IzoOT4h-k";
        window.localStorage.setItem('token', userToken);

        cy.visit('http://localhost:4200/admin')
        cy.contains('Admin Panel')
    })

})