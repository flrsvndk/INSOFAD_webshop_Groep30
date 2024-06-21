describe('Retour Pagina tests', () => {

  beforeEach(() => {
    cy.intercept('POST', '/api/auth/login', { fixture: 'login.json' }).as('login');
    cy.intercept('GET', '/api/auth/user', { fixture: 'user.json' }).as('user');

    cy.visit('/auth/login');
    cy.get('input[id=email]').type('bob@bobsluxuryenterprise.com');
    cy.get('input[id=password1]').type('MegaGeheim6789!!');
    cy.get('button[id=login]').click();
  });

  it('should pass', () => {
    cy.intercept('GET', '/api/orders/myOrders', { fixture: 'orders.json' }).as('orders');

    cy.visit('/order-history');
  });
})