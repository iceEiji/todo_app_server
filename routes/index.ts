import { Request, Response, NextFunction } from "express";
import express from "express";
const router = express.Router();

/* GET home page. */
router.get('/', function(req: Request, res: Response, next: NextFunction) {
  res.render('index', { title: 'Express' });
});

export {router};
