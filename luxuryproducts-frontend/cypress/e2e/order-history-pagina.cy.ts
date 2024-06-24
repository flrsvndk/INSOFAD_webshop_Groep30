describe('Order History Pagina tests', () => {

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
  };

  beforeEach(() => {
    interceptRoutes();
    login();
  });

  it('should filter on search input', () => {
    cy.get('a[id=profile-button]').click();
    cy.get('a[id=order-history]').click();
    cy.get('input[id=search-input]').type('1cc5c76a-7848-42eb-8b50-9029ef608f12');
    cy.get('button[id=submit-search]').click();

    cy.contains('1cc5c76a-7848-42eb-8b50-9029ef608f12').should('be.visible');
    cy.contains('3d27eddd-8155-411c-8c1b-d26bcec6c798').should('not.exist');
  });

});
