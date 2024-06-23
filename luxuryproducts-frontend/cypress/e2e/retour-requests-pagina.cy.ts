describe('Retour Requests Pagina tests', () => {

  const login = () => {
    cy.visit('/auth/login');
    cy.get('input[id=email]').type('bob@bobsluxuryenterprise.com');
    cy.get('input[id=password1]').type('MegaGeheim6789!!');
    cy.get('button[id=login]').click();
  };

  const interceptRoutes = () => {
    cy.intercept('POST', '/api/auth/login', { fixture: 'login.json' }).as('login');
    cy.intercept('GET', '/api/auth/user', { fixture: 'user.json' }).as('user');
    cy.intercept('GET', '/api/auth/user/role', { body: 'ADMIN' }).as('role');
    cy.intercept('GET', '/api/retour/requests', { fixture: 'requests.json' }).as('requests');
  };

  beforeEach(() => {
    interceptRoutes();
    login();
  });

  it('should filter on search input', () => {
    cy.visit('admin/retours');

    cy.get('select').select(1);
    cy.contains('5d56e310-45e2-46b1-9ef8-05797ef76c99').should('be.visible');

    cy.get('select').select(2);
    cy.contains('5d56e310-45e2-46b1-9ef8-05797ef76c99').should('not.exist');
  });

});
