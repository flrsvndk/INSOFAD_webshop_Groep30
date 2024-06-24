describe('Retour Pagina tests', () => {

  const login = () => {
    cy.visit('/auth/login');
    cy.get('input[id=email]').type('bob@bobsluxuryenterprise.com');
    cy.get('input[id=password1]').type('MegaGeheim6789!!');
    cy.get('button[id=login]').click();
  };

  const interceptRoutes = () => {
    cy.intercept('POST', '/api/auth/login', { fixture: 'login.json' }).as('login');
    cy.intercept('GET', '/api/auth/user', { fixture: 'user.json' }).as('user');
    cy.intercept('GET', '/api/orders/myOrders', { fixture: 'orders.json' }).as('orders');
    cy.intercept('GET', '/api/retour/reasons', { fixture: 'reasons.json' }).as('reasons');
    cy.intercept('POST', '/api/retour', { message: 'request sent successfully' }).as('retour');
  };

  const submitReturnRequest = (reasonIndex: number) => {
    const expectedUrl = 'http://localhost:4200/';

    cy.visit('/order-history');
    cy.get('a[id=return-order-button]').click();
    cy.get('input[type=checkbox]').click();
    cy.get('select').select(reasonIndex);
    cy.get('button[id=send-request-button]').click();
    cy.url().should('eq', expectedUrl);
  };

  beforeEach(() => {
    interceptRoutes();
    login();
  });

  it('should succeed when selecting reason 1', () => {
    submitReturnRequest(1);
  });

  it('should succeed when selecting reason 2', () => {
    submitReturnRequest(2);
  });

  it('should succeed when selecting reason 3', () => {
    submitReturnRequest(3);
  });

  it('should succeed when selecting reason 4', () => {
    submitReturnRequest(4);
  });

});
