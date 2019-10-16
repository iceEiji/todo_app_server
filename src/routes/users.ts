import express, { Request, Response, NextFunction, Router } from "express";
const router: Router = express.Router();

/* GET users listing. */
router.get('/', (req: Request, res: Response, next: NextFunction) => {
  res.render('users', { title: 'Express - users' });
  // res.send('respond with a resource');
});

export {router as users};
