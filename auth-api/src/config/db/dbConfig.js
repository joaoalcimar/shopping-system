import {Sequelize} from "sequelize";
import {DB_NAME, DB_PORT, DB_PASSWORD, DB_HOST, DB_USER, DB_DIALECT} from "../constants/secrets.js";

const sequelize = new Sequelize(DB_NAME,DB_USER,DB_PASSWORD, {
    host: DB_HOST,
    port: DB_PORT,
    dialect: DB_DIALECT,
    quoteIdentifiers: false,
    define:{
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true
    },
    pool: {
        acquire: 180000
    }
});

sequelize
    .authenticate()
    .then(() => {
        console.info("Connection has been established!");
    })
    .catch((err) => {
        console.error("Unable to connect database");
        console.error(err.message);
    })

export default sequelize;