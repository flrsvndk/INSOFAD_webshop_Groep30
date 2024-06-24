describe("Promocode tests", () => {
    const login = () => {
        cy.visit('/auth/login');
        cy.get('input[id=email]').type('bob@bobsluxuryenterprise.com');
        cy.get('input[id=password1]').type('MegaGeheim6789!!');
        cy.get('button[id=login]').click();
        cy.wait('@login');
    };

    const interceptRoutes = () => {
        cy.intercept('POST', '/api/auth/login').as('login');
        cy.intercept('GET', '/api/auth/user', { fixture: 'user.json' }).as('user');
        cy.intercept('GET', '/api/products').as('product');
        cy.intercept('GET', '/api/promocodes', { fixture: 'promocodes.json' }).as('promocodes');
        cy.intercept('PUT', '/api/promocodes/f8caa349-3f77-4b16-997e-d416a05f5d67', { statusCode: 200 });
        cy.intercept('DELETE', '/api/promocodes/f8caa349-3f77-4b16-997e-d416a05f5d67', { statusCode: 200 });
        cy.intercept('GET', '/api/promocodes/f8caa349-3f77-4b16-997e-d416a05f5d67', { fixture: 'createdPromocode.json' }).as('createdPromocode');
        cy.intercept('GET', '/api/promocodes/612f1cf0-e126-4837-9006-e53b16229b8c', { fixture: 'existingPromocode.json' }).as('existingPromocode');
        cy.intercept('PUT', '/api/promocodes/update', { statusCode: 200 });
    };

    const addProductToCart = () => {
        cy.wait('@product');
        cy.get('app-product-thumbnail', { timeout: 10000 }).contains('Tesla Model S Plaid').parents('app-product-thumbnail')
            .within(() => {
                cy.contains('Show Detail').click();
            });
        cy.url().should('include', '/products/');
        cy.get('button').contains('Elektrisch').click();
        cy.get('button').contains('4').click();
        cy.get('button').contains('Voeg toe aan winkelmand').click();
    };

    const applyPromocode = (promocode: string) => {
        cy.get('input[formControlName=Promocode]').type(promocode);
        cy.get('button[id=promocode]').click();
    };

    const createPromocode = () => {
        cy.get('input[formcontrolname="name"]').should('be.visible').type('TESTPROMOCODE123');
        cy.get('input[formcontrolname="description"]').type('Testbeschrijving voor de Promocode! 10%!');
        cy.get('input[formcontrolname="percentageOff"]').type('10');
        cy.get('input[formcontrolname="maxUsages"]').type('100');
        cy.get('input[formcontrolname="dedicatedPromocode"]').check();
        cy.get('input[formcontrolname="dedicatedUserEmail"]').type('user@example.com');
        cy.get('button[type="button"]').click();
    };

    const editPromocode = () => {
        cy.wait("@createdPromocode");
        cy.get('input[formcontrolname="name"]').clear().type('TESTCHANGEDPROMOCODE123');
        cy.get('button[type="button"]').click();
        cy.visit('admin/promocodes');
    };

    const editPromocodeWithError = () => {
        cy.wait("@existingPromocode");
        cy.get('input[formcontrolname="name"]').clear().type('TESTCHANGEDPROMOCODE123');
        cy.get('button[type="button"]').click();
    };

    const interceptAndVisitPromocodes = (fixture: string) => {
        cy.intercept('GET', '/api/promocodes', { fixture }).as('updatedPromocodes');
        cy.visit('/admin/promocodes');
        cy.wait('@updatedPromocodes');
    };

    beforeEach(() => {
        interceptRoutes();
        login();
    });

    it('should add a product to the cart and apply promocode', () => {
        cy.visit('/products');
        addProductToCart();
        cy.visit('cart');
        cy.get('p[id=totalPriceWithoutPromocodeAndGiftCards]').should('contain', '€130,000.00');
        applyPromocode('SUMMERSALE10');
        cy.get('p[id=totalPriceWithPromocode]').should('contain', '€117,000.00');
    });

    it('should add a product to the cart and give an error when applying a wrong promocode', () => {
        cy.visit('/products');
        addProductToCart();
        cy.visit('cart');
        applyPromocode('WRONGCODE23');
        cy.get('p[id=promocodeError]').should('contain', 'Fout bij verwerken promocode');
    });

    it('should create promocode TESTPROMOCODE123', () => {
        cy.visit('admin/create-promocode');
        cy.wait('@promocodes');

        createPromocode();

        interceptAndVisitPromocodes('promocodesWithTestPromocodeAdded.json');
        cy.contains('app-promocode-thumbnail', 'TESTPROMOCODE123').within(() => {
            cy.get('[id="available"]').should('contain', 'Beschikbaar');
        });
    });

    it('should give error when creating promocode TESTPROMOCODE123', () => {
        cy.intercept('GET', '/api/promocodes', { fixture: 'promocodesWithTestPromocodeAdded.json' }).as('addedPromocodes');
        cy.visit('admin/create-promocode');
        cy.wait('@addedPromocodes');

        createPromocode();

        cy.get('[role=alert]').should('contain', "Deze promotiecode bestaat al!");
    });

    it('should archive promocode TESTPROMOCODE123 after clicking archive button', () => {
        interceptAndVisitPromocodes('promocodesWithTestPromocodeAdded.json');

        cy.contains('app-promocode-thumbnail', 'TESTPROMOCODE123').within(() => {
            cy.get('[id="available"]').should('contain', 'Beschikbaar');
            cy.get('#archive').click();
        });

        interceptAndVisitPromocodes('promocodesWithUnavailableTestPromocode.json');
        cy.contains('app-promocode-thumbnail', 'TESTPROMOCODE123').within(() => {
            cy.get('[id="notAvailable"]').should('contain', 'Niet Beschikbaar');
        });
    });

    it('should unarchive promocode TESTPROMOCODE123 after clicking unarchive button', () => {
        interceptAndVisitPromocodes('promocodesWithUnavailableTestPromocode.json');

        cy.contains('app-promocode-thumbnail', 'TESTPROMOCODE123').within(() => {
            cy.get('[id="notAvailable"]').should('contain', 'Niet Beschikbaar');
            cy.get('#unarchive').click();
        });

        interceptAndVisitPromocodes('promocodesWithTestPromocodeAdded.json');
        cy.contains('app-promocode-thumbnail', 'TESTPROMOCODE123').within(() => {
            cy.get('[id="available"]').should('contain', 'Beschikbaar');
        });
    });

    it('should edit promocode', () => {
        interceptAndVisitPromocodes('promocodesWithTestPromocodeAdded.json');

        cy.contains('app-promocode-thumbnail', 'TESTPROMOCODE123').within(() => {
            cy.get('#edit').click();
        });

        editPromocode();

        interceptAndVisitPromocodes('promocodesWithEditedPromocode.json');
        cy.contains('app-promocode-thumbnail', 'TESTCHANGEDPROMOCODE123').within(() => {
            cy.get('[id="available"]').should('contain', 'Beschikbaar');
        });
    });

    it('should give error when editing promocode', () => {
        interceptAndVisitPromocodes('promocodesWithEditedPromocode.json');

        cy.contains('app-promocode-thumbnail', 'WINTERSALE25').within(() => {
            cy.get('#edit').click();
        });

        editPromocodeWithError();

        cy.get('[role=alert]').should('contain', "Deze promotiecode bestaat al!");
    });

    it('should delete promocode TESTCHANGEDPROMOCODE123 after clicking delete button', () => {
        interceptAndVisitPromocodes('promocodesWithEditedPromocode.json');

        cy.contains('app-promocode-thumbnail', 'TESTCHANGEDPROMOCODE123').within(() => {
            cy.get('#delete').click();
        });

        interceptAndVisitPromocodes('promocodes.json');
        cy.contains('app-promocode-thumbnail', 'TESTCHANGEDPROMOCODE123').should('not.exist');
    });
});
