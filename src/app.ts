import express, { Request, Response, NextFunction } from 'express';
import createError from 'http-errors';
import cookieParser from 'cookie-parser';
import logger from 'morgan';
import i18n from 'i18n';

import {index} from './routes/index';
import {users} from './routes/users';

const app = express();

// view engine setup
app.set('views', 'views');
app.set('view engine', 'pug');

// express
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static('public'));


// i18n
i18n.configure({
  locales: ['ja', 'en'],
  defaultLocale: 'ja',
  directory: "locales",
  objectNotation: true
});
app.use(i18n.init);
app.use(function (req: Request, res: Response, next: NextFunction) {
  const session = req.session;
  if (session != null && session.locale) {
    i18n.setLocale(req, session.locale);
  }
  next();
});

// routing setup
app.use('/', index);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function(req: Request, res: Response, next: NextFunction) {
  next(createError(404));
});

// error handler
app.use(function(err: any, req: Request, res: Response, next: NextFunction) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

export {app};
